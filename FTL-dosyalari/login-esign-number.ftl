<#-- Import template and stylesheet -->
<#import "template.ftl" as layout>
<link rel="stylesheet" href="${url.resourcesPath}/login/style.css">

<@layout.registrationLayout displayMessage=false displayInfo=false; section>
    <#if section == "header">
        <div id="kc-header">
            <img src="${url.resourcesPath}/img/img.png" alt="Keycloak Logo" class="logo">
            <h1>Elektronik İmza İşlem Kodunuz</h1>
            <p>E-İmza işlemi için aşağıdaki kodu kullanarak giriş yapabilirsiniz.</p>
        </div>
    <#elseif section == "form">
        <div id="kc-form">
            <form id="verification-form" method="post" action="${url.loginAction}">
                <div id="kc-form-wrapper">
                    <p class="info-text">
                        İşlem kodunuz aşağıda görüntülenmiştir. Lütfen bu kodu e-imza uygulamasına girin ve işlemi tamamlayın.
                    </p>

                    <div class="transaction-container">
                        <p><strong>İşlem Kodunuz:</strong></p>
                        <div class="transaction-code">${transactionCode}</div>
                    </div>
                    
                    <!-- Bekleme Logosu -->
                    <div id="loading-status">
                        <p style="color: #666; margin-top: 10px;">Doğrulama bekleniyor...</p>
                    </div>

                    <p id="verification-status" style="text-align: center; display: none; color: red;"></p>
                </div>
            </form>
        </div>

        <#-- Footer -->
        <div id="kc-footer">
            <div id="footer-content">
                <span>© 2024 E-İmza Servisi. Tüm hakları saklıdır.</span>
                <a href="#">Gizlilik Politikası</a> |
                <a href="#">Kullanım Şartları</a> |
                <a href="#">İletişim</a>
            </div>
        </div>
    </#if>

    <style>
        .info-text {
            text-align: center;
            margin-bottom: 20px;
            font-size: 14px;
            color: #555;
        }

        .transaction-container {
            text-align: center;
            margin: 20px 0;
        }

        .transaction-code {
            font-size: 32px;
            font-weight: bold;
            color: #333;
            border: 1px solid #ccc;
            padding: 10px 20px;
            margin: 10px auto;
            display: inline-block;
            background: #f9f9f9;
            border-radius: 5px;
        }

        #loading-status {
            text-align: center;
            margin-top: 20px;
        }

        #verification-status {
            margin-top: 20px;
            font-size: 14px;
        }

        #kc-footer {
            margin-top: 20px;
            text-align: center;
            font-size: 12px;
            color: #777;
        }

        #kc-footer a {
            color: #007BFF;
            text-decoration: none;
        }
        
        #kc-attempted-username {
            display: none;
        }

        #reset-login{
            display: none;
        }
    </style>

    <!-- JavaScript -->
    <script>
        document.addEventListener("DOMContentLoaded", () => {
            const transactionCodeElement = document.querySelector(".transaction-code");
            const statusMessage = document.getElementById("verification-status");
            const loadingStatus = document.getElementById("loading-status");
            let intervalId;

            if (transactionCodeElement) {
                const transactionCode = transactionCodeElement.innerText;

                async function checkVerificationStatus() {
                    console.log("Doğrulama kontrolü başlatıldı...");
                    try {
                        const response = await fetch("http://127.0.0.1:7248/api/TransactionCode/verify", {
                            method: "POST",
                            headers: { "Content-Type": "application/json" },
                            body: JSON.stringify({ transactionCode: transactionCode })
                        });

                        console.log("Fetch isteği gönderildi.");
                        const result = await response.json();
                        console.log("API Yanıtı:", result);

                        if (result && result.status === "verified") {
                            clearInterval(intervalId);
                            statusMessage.style.color = "green";
                            statusMessage.innerText = "Kod doğrulandı. Giriş işlemi tamamlanıyor...";
                            loadingStatus.style.display = "none";
                            document.getElementById("verification-form").submit();
                        } else {
                            console.log("Kod doğrulanmadı, tekrar kontrol ediliyor...");
                        }
                    } catch (error) {
                        console.error("Doğrulama sırasında hata oluştu:", error);
                        statusMessage.style.color = "red";
                        statusMessage.innerText = "Bir hata oluştu. Lütfen bağlantınızı kontrol edin.";
                    }
                }

                intervalId = setInterval(checkVerificationStatus, 5000);
            } else {
                console.error("Transaction code element not found!");
            }
        });
    </script>

</@layout.registrationLayout>
