using SOApi.DTO;
using SOApi.Models;

namespace SOApi.Interfaces
{
    public interface ITagService
    {
        Task<List<Tag>> DownloadTagsFromStackOverflow();
        Task<IDictionary<string, double>> CalculatePercentage();
        Task<List<TagDTO>> GetTags(int? page = 1, int? pageSize = 10, string sortBy = "Name", string sortOrder = "asc");

    }
}
