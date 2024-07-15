using Microsoft.EntityFrameworkCore;

namespace SOApi.Models
{
    public class TagContext : DbContext
    {
        public TagContext(DbContextOptions<TagContext> options) : base(options)
        {
        }

        public DbSet<Tag> Tags { get; set; } = null!;
        public DbSet<Collectives> Collectives { get; set; } = null!;
        public DbSet<CollectiveExternalLink> CollectiveExternalLinks { get; set; } = null!;
        public DbSet<User> Users { get; set; }

    }
}
