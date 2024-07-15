using Microsoft.EntityFrameworkCore;
using SOApi.Interfaces;
using SOApi.Models;
using SOApi.Services;

var builder = WebApplication.CreateBuilder(args);

// Add services to the container.
builder.Services.AddDbContext<TagContext>(options => options.UseSqlite(builder.Configuration.GetConnectionString("TagContext")));
builder.Services.AddHttpClient("httpClient");
builder.Services.AddScoped<ITokenService, TokenService>();
builder.Services.AddScoped<ITagService, TagService>();
builder.Services.AddControllers();
// Learn more about configuring Swagger/OpenAPI at https://aka.ms/aspnetcore/swashbuckle
builder.Services.AddEndpointsApiExplorer();
builder.Services.AddSwaggerGen();
var app = builder.Build();

// Configure the HTTP request pipeline.
if (app.Environment.IsDevelopment())
{
    app.UseSwagger();
    app.UseSwaggerUI();
}

app.UseHttpsRedirection();
app.UseAuthentication();
app.UseAuthorization();


app.MapControllers();

app.Run();
