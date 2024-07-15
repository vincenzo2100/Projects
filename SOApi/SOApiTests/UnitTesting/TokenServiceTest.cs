using Microsoft.Extensions.Configuration;
using Moq;
using SOApi.Models;
using SOApi.Services;
using System;
using System.Collections.Generic;
using System.IdentityModel.Tokens.Jwt;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace SOApiTests.UnitTesting
{
    public class TokenServiceTest
    {
        private IConfiguration GetConfiguration(string tokenKey)
        {
            var configuration = new Mock<IConfiguration>();
            configuration.Setup(x => x["TokenKey"]).Returns(tokenKey);
            return configuration.Object;
        }

        [Fact]
        public void CreateToken_ValidUser_ReturnsToken()
        {
            // Arrange
            var tokenKey = "my_secret_token_key";
            var configuration = GetConfiguration(tokenKey);
            var tokenService = new TokenService(configuration);
            var user = new User { UserName = "testuser" };

            // Act
            var token = tokenService.CreateToken(user);

            // Assert
            Assert.NotNull(token);
            var tokenHandler = new JwtSecurityTokenHandler();
            var decodedToken = tokenHandler.ReadJwtToken(token);
            Assert.Equal(user.UserName, decodedToken.Claims.FirstOrDefault(c => c.Type == JwtRegisteredClaimNames.NameId)?.Value);
        }




        [Fact]
        public void CreateToken_SuccessfulLogin_ReturnsValidToken()
        {
            // Arrange
            var tokenKey = "my_secret_token_key";
            var configuration = GetConfiguration(tokenKey);
            var tokenService = new TokenService(configuration);
            var user = new User { UserName = "testuser" };

            // Act
            var token = tokenService.CreateToken(user);

            // Assert
            Assert.NotNull(token);
            var tokenHandler = new JwtSecurityTokenHandler();
            var decodedToken = tokenHandler.ReadJwtToken(token);
            Assert.True(decodedToken.ValidTo > DateTime.UtcNow);
            Assert.Equal(user.UserName, decodedToken.Claims.FirstOrDefault(c => c.Type == JwtRegisteredClaimNames.NameId)?.Value);

        }
    }
}
