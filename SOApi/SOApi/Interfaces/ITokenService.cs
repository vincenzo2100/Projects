using SOApi.Models;

namespace SOApi.Interfaces
{
    public interface ITokenService
    {
        string CreateToken(User user);
    }
}
