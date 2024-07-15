using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using Newtonsoft.Json;
using SOApi.DTO;
using SOApi.Interfaces;
using SOApi.Models;
using System.IO.Compression;
using System.Net.Http;

namespace SOApi.Services
{
    public class TagService : ITagService
    {
        private readonly TagContext _tagContext;
        private readonly HttpClient _httpClient;
        private string? _url = $"https://api.stackexchange.com/2.3/tags?&order=desc&sort=popular&site=stackoverflow";
        public TagService(TagContext tagContext,HttpClient httpClient)
        {
            _tagContext = tagContext;
            _httpClient = httpClient;
        }

        public async Task<List<Tag>> DownloadTagsFromStackOverflow()
        {
            int pageSize = 100;
            int numberOfTagsToDownload = 1000;
            int totalPages = (int)Math.Ceiling((double)numberOfTagsToDownload / pageSize);
            int totalTagsSaved = 0;
            var body = new List<Tag>();

            for (int apiPage = 1; apiPage <= totalPages; apiPage++)
            {
                var response = await _httpClient.GetAsync($"{_url}&page={apiPage}&pagesize={pageSize}");
                if (response.IsSuccessStatusCode)
                {
                    var contentStream = await response.Content.ReadAsStreamAsync();

                    // Check if content is compressed (e.g., gzip)
                    if (response.Content.Headers.ContentEncoding.Contains("gzip"))
                    {
                        using (var decompressionStream = new GZipStream(contentStream, CompressionMode.Decompress))
                        using (var streamReader = new StreamReader(decompressionStream))
                        using (var jsonReader = new JsonTextReader(streamReader))
                        {
                            var serializer = new JsonSerializer();
                            var tagResponse = serializer.Deserialize<TagResponse>(jsonReader);

                            // Save tags to the database
                            foreach (var item in tagResponse.items)
                            {
                                var tag = new Tag
                                {
                                    Collectives = item.Collectives,
                                    Count = item.Count,
                                    Has_Synonyms = item.Has_Synonyms,
                                    Is_Moderator_Only = item.Is_Moderator_Only,
                                    Is_Required = item.Is_Required,
                                    Last_Activity_Date = item.Last_Activity_Date,
                                    Name = item.Name,
                                    Synonyms = item.Synonyms,
                                    User_Id = item.User_Id,
                                };

                                _tagContext.Tags.Add(tag);
                                body.Add(tag);
                            }

                            await _tagContext.SaveChangesAsync();
                            totalTagsSaved += tagResponse.items.Count;
                        }
                    }
                }
                else
                {
                    throw new HttpRequestException("Cannot connect to Stack Exchange API");
                }
            }

            
            return body;
        }

        public async Task<IDictionary<string, double>> CalculatePercentage()
        {
            var tags = await _tagContext.Tags.ToListAsync();
            var percentageDict = new Dictionary<string, double>();
            long totalTagCounts = tags.Sum(tag => tag.Count);

            foreach (var tag in tags)
            {
                if (tag.Name != null && !percentageDict.ContainsKey(tag.Name))
                {
                    double percentageShare = (tag.Count / (double)totalTagCounts) * 100;
                    percentageDict.Add(tag.Name, percentageShare);
                }
            }
            return percentageDict;
        }

        public async Task<List<TagDTO>> GetTags(int? page = 1, int? pageSize = 10, string sortBy = "Name", string sortOrder = "asc")
        {
            try
            {
                var query = from tag in _tagContext.Tags
                            join collective in _tagContext.Collectives
                            on tag.Id equals collective.TagId into collectives
                            from collective in collectives.DefaultIfEmpty()
                            select new TagDTO
                            {
                                Tag = tag,
                                Collective = collective,
                                ExternalLinks = collective != null ? collective.External_Links : null
                            };

                // Sorting
                if (!string.IsNullOrEmpty(sortBy))
                {
                    switch (sortBy.ToLower())
                    {
                        case "name":
                            query = sortOrder.ToLower() == "desc" ? query.OrderByDescending(t => t.Tag.Name) : query.OrderBy(t => t.Tag.Name);
                            break;
                        case "count":
                            query = sortOrder.ToLower() == "desc" ? query.OrderByDescending(t => t.Tag.Count) : query.OrderBy(t => t.Tag.Count);
                            break;
                        case "id":
                            query = sortOrder.ToLower() == "desc" ? query.OrderByDescending(t => t.Tag.Id) : query.OrderBy(t => t.Tag.Id);
                            break;
                        default:
                            query = query.OrderBy(t => t.Tag.Name);
                            break;
                    }
                }

                // Pagination
                if (page.HasValue && pageSize.HasValue)
                {
                    var totalCount = await query.CountAsync();
                    var totalPages = (int)Math.Ceiling(totalCount / (double)pageSize);

                    if (page < 1 || page > totalPages)
                    {
                        throw new InvalidOperationException("Invalid page number.");
                    }

                    query = query.Skip((page.Value - 1) * pageSize.Value).Take(pageSize.Value);
                }

                
                

                var tags = await query.ToListAsync();
                return tags;
            }
            catch (Exception ex)
            {
                throw new Exception("An error occurred while fetching tags.", ex);
            }
        }

    }
}
