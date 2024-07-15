namespace SOApi.Models
{
    public class Collectives
    {
        public int Id { get; set; }
        public string? Description { get; set; }
        public List<CollectiveExternalLink>? External_Links { get; set; }
        public string? Link { get; set; }
        public string? Name { get; set; }
        public string? Slug { get; set; }
        public string[]? Tags { get; set; }

        public int TagId { get; set; }
    }
}
