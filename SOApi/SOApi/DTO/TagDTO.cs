using SOApi.Models;

namespace SOApi.DTO
{
    public class TagDTO
    {
        public Tag Tag { get; set; }
        public Collectives Collective { get; set; }
        public List<CollectiveExternalLink> ExternalLinks { get; set; }
    }
}
