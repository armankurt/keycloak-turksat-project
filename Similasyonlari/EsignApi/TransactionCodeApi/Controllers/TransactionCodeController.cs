using Microsoft.AspNetCore.Mvc;
using System.Collections.Concurrent;

namespace TransactionCodeApi.Controllers
{
    [ApiController]
    [Route("api/[controller]")]
    public class TransactionCodeController : ControllerBase
    {
        // Kullanıcı işlem kodlarını ve durumlarını saklayan yapı
        private static readonly ConcurrentDictionary<string, TransactionCodeData> TransactionCodes = new();

        // Keycloak tarafından gönderilen işlem kodunu doğrulama
        [HttpPost("verify")]
        public IActionResult VerifyTransactionCode([FromBody] TransactionCodeRequest request)
        {
            if (request == null || string.IsNullOrEmpty(request.TransactionCode))
            {
                return Ok(new TransactionCodeResponse
                {
                    Status = "error",
                    Message = "Transaction code is required."
                });
            }

            if (TransactionCodes.TryGetValue(request.TransactionCode, out TransactionCodeData data))
            {
                // Kullanıcının doğrulama yapıp yapmadığını kontrol et
                if (data.IsVerified)
                {
                    return Ok(new TransactionCodeResponse
                    {
                        Status = "verified",
                        Message = "Transaction code has been verified."
                    });
                }
                else
                {
                    return Ok(new TransactionCodeResponse
                    {
                        Status = "pending",
                        Message = "Transaction code verification pending."
                    });
                }
            }

            return Ok(new TransactionCodeResponse
            {
                Status = "error",
                Message = "Invalid transaction code."
            });
        }

        // Keycloak'ın oluşturduğu işlem kodunu kaydetme
        [HttpPost("store")]
        public IActionResult StoreTransactionCode([FromBody] StoreTransactionCodeRequest request)
        {
            if (request == null || string.IsNullOrEmpty(request.TransactionCode) || string.IsNullOrEmpty(request.SessionId))
            {
                return Ok(new TransactionCodeResponse
                {
                    Status = "error",
                    Message = "Transaction code and session ID are required."
                });
            }

            TransactionCodes[request.TransactionCode] = new TransactionCodeData
            {
                SessionId = request.SessionId,
                IsVerified = false
            };

            return Ok(new TransactionCodeResponse
            {
                Status = "stored",
                Message = "Transaction code stored successfully."
            });
        }

        // Kullanıcının işlem kodunu doğrulaması
        [HttpPost("user-verify")]
        public IActionResult UserVerifyTransactionCode([FromBody] TransactionCodeRequest request)
        {
            if (request == null || string.IsNullOrEmpty(request.TransactionCode))
            {
                return Ok(new TransactionCodeResponse
                {
                    Status = "error",
                    Message = "Transaction code is required."
                });
            }

            if (TransactionCodes.TryGetValue(request.TransactionCode, out TransactionCodeData data))
            {
                data.IsVerified = true; // Kullanıcı doğrulama yaptı
                return Ok(new TransactionCodeResponse
                {
                    Status = "verified",
                    Message = "Transaction code verified successfully."
                });
            }

            return Ok(new TransactionCodeResponse
            {
                Status = "error",
                Message = "Invalid transaction code."
            });
        }
    }

    // Veri modeli
    public class TransactionCodeData
    {
        public string SessionId { get; set; }
        public bool IsVerified { get; set; }
    }

    // Request modelleri
    public class TransactionCodeRequest
    {
        public string TransactionCode { get; set; }
    }

    public class StoreTransactionCodeRequest
    {
        public string TransactionCode { get; set; }
        public string SessionId { get; set; }
    }

    public class TransactionCodeResponse
    {
        public string Status { get; set; }
        public string Message { get; set; }
    }
}
