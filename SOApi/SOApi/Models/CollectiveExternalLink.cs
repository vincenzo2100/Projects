namespace SOApi.Models
{
    public class CollectiveExternalLink
    {
        public int Id { get; set; }
        public string Link { get; set; }
        
        public WebsitesType Type { get; set; }

       
    }

    public enum WebsitesType
    {
        website,
        twitter,
        github,
        facebook,
        instagram,
        support,
        linkedin,
    }
}
