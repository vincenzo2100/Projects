using Microsoft.EntityFrameworkCore;
using Moq;
using SOApi.Models;
using SOApi.Services;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Text;
using System.Threading.Tasks;

namespace SOApiTests.UnitTesting
{
    public class TagServiceTest
    {
        [Fact]
        public async Task CalculatePercentage_Success()
        {
            // Arrange
            var tags = new List<Tag>
            {
                new Tag { Name = "tag1", Count = 10 },
                new Tag { Name = "tag2", Count = 20 },
                new Tag { Name = "tag3", Count = 30 }
            };

            var options = new DbContextOptionsBuilder<TagContext>()
                .UseInMemoryDatabase(databaseName: "TestDatabase")
                .Options;

            using (var context = new TagContext(options))
            {
                await context.Tags.AddRangeAsync(tags);
                await context.SaveChangesAsync();

                var service = new TagService(context, null);

                // Act
                var percentageDict = await service.CalculatePercentage();

                // Assert
                Assert.NotNull(percentageDict);
                Assert.Equal(3, percentageDict.Count);
                Assert.True(percentageDict.ContainsKey("tag1"));
                Assert.True(percentageDict.ContainsKey("tag2"));
                Assert.True(percentageDict.ContainsKey("tag3"));
                Assert.Equal(16.67, percentageDict["tag1"], 2);
                Assert.Equal(33.33, percentageDict["tag2"], 2);
                Assert.Equal(50.00, percentageDict["tag3"], 2);
            }
        }


    }
}
