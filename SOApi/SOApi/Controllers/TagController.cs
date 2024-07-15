using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using Newtonsoft.Json;
using SOApi.Interfaces;
using SOApi.Models;
using SOApi.Services;
using System.IO.Compression;



namespace SOApi.Controllers
{
   
    
    [Route("api/tag")]
    [ApiController]
    public class TagController : ControllerBase
    {
        private readonly TagContext _tagContext;
        private readonly HttpClient _httpClient;
        private readonly ITagService _tagService;
        private string? _url = $"https://api.stackexchange.com/2.3/tags?&order=desc&sort=popular&site=stackoverflow";

        public TagController(TagContext tagContext, HttpClient httpClient,ITagService tagService)
        {
            _tagContext = tagContext;
            _httpClient = httpClient;
            _tagService = tagService;
        }



        

        [Route("Download1000Tags")]
        [HttpGet]
        public async Task<IActionResult> Download1000Tags()
        {
            try
            {
                var tags = await _tagService.DownloadTagsFromStackOverflow();
                return Ok(tags);
            }
            catch (HttpRequestException ex)
            {
                return BadRequest($"Something went wrong: {ex.Message}");
            }
        }

        [Route("CountPercentage")]
        [HttpGet]
        public async Task<IActionResult> CalculatePercentage()
        {
            try
            {
                var tags = await _tagService.CalculatePercentage();
                return Ok(tags);
            }
            catch (HttpRequestException ex)
            {
                return BadRequest($"Something went wrong: {ex.Message}");
            }
        }

        [Route("Pagination")]
       
        [HttpGet]
        public async Task<ActionResult> GetTags(
            [FromQuery] int? page = 1,
            [FromQuery] int? pageSize = 10,
            [FromQuery] string sortBy = "Name",
            [FromQuery] string sortOrder = "asc")
        {
            try
            {
                var tags = await _tagService.GetTags(page, pageSize, sortBy, sortOrder);
                return Ok(tags);
            }
            catch (Exception ex)
            {
                return StatusCode(500, $"An error occurred: {ex.Message}");
            }
        }




    }
}

    


