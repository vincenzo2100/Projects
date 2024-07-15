using Newtonsoft.Json;

namespace SOApi.Models
{
    public class Tag
    {
        public int? Id { get; set; }
        public List<Collectives>? Collectives { get; set; }
        public int Count { get; set; }
        public bool Has_Synonyms { get; set; }
        public bool Is_Moderator_Only { get; set; }
        public bool Is_Required { get; set; }
        public DateTime? Last_Activity_Date { get; set; }
        public string? Name { get; set; }
        public string[]? Synonyms { get; set; }
        public int? User_Id { get; set; }
    }
}
