using Newtonsoft.Json;
using SOApi.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace SOApiTests.IntegrationTesting
{
    public class TagControllerTest
    {
        private readonly HttpClient _client;

        public TagControllerTest()
        {
            _client = new HttpClient();
            _client.BaseAddress = new Uri("https://localhost:7230/api/tag/");
        }

        [Fact]
        public async Task Download1000Tags_ReturnsListOfTags()
        {
            // Act
            var response = await _client.GetAsync("/api/tag/Download1000Tags");

            // Assert
            response.EnsureSuccessStatusCode(); // Status Code 200-299
            Assert.True(response.IsSuccessStatusCode);

            var content = await response.Content.ReadAsStringAsync();
            var tags = JsonConvert.DeserializeObject<List<Tag>>(content);

            // Add your assertions here
            Assert.NotNull(tags);
            Assert.NotEmpty(tags);
        }


        [Fact]
        public async Task CountPercatage_ReturnDict()
        {
            // Act
            var response = await _client.GetAsync("/api/tag/CountPercentage");

            // Assert
            response.EnsureSuccessStatusCode(); // Status Code 200-299
            Assert.True(response.IsSuccessStatusCode);

            var content = await response.Content.ReadAsStringAsync();
            var tags = JsonConvert.DeserializeObject<IDictionary<string,double>>(content);

            // Add your assertions here
            Assert.NotNull(tags);
            Assert.NotEmpty(tags);
        }

        [Fact]
        public async Task GetTags_ReturnsTagsWithPaginationAndSorting()
        {
            // Arrange
            var page = 1;
            var pageSize = 10;
            var sortBy = "Name";
            var sortOrder = "asc";

            // Act
            var response = await _client.GetAsync($"/api/tag/Pagination?page={page}&pageSize={pageSize}&sortBy={sortBy}&sortOrder={sortOrder}");

            // Assert
            response.EnsureSuccessStatusCode(); // Status Code 200-299
            Assert.True(response.IsSuccessStatusCode);

            var content = await response.Content.ReadAsStringAsync();
            var tags = JsonConvert.DeserializeObject<List<Tag>>(content);

            // Add your assertions here
            Assert.NotNull(tags);
            Assert.Equal(pageSize, tags.Count); // Assuming you're returning the correct page size
            // Perform more assertions based on your requirements
        }
    }



}

