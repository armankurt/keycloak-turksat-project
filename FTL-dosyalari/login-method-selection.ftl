<#import "template.ftl" as layout>

<div id="main-content" style="display: flex; flex-direction: column; align-items: center; justify-content: center; min-height: 100vh; font-family: 'Arial', sans-serif; opacity: 0; background-color: #ffffff; transition: all 0.6s ease;">

    <div style="text-align: center; margin-bottom: 30px;">
        <h1 style="font-size: 36px; color: #2c3e50; font-weight: 800; margin: 0;">Projem Kapısı</h1>
        <h3 style="font-size: 22px; color: #7f8c8d; font-weight: 500; margin-top: 8px;">İki Adımlı Doğrulama Seçimi</h3>
        <p style="font-size: 16px; color: #95a5a6; margin-top: 12px; max-width: 500px; margin-left: auto; margin-right: auto;">Lütfen doğrulama yöntemini seçiniz. Güvenliğiniz için bu adımı tamamlayın.</p>
    </div>

    <div style="display: flex; flex-direction: column; gap: 20px; width: 100%; max-width: 450px; padding: 30px; margin-top: -20px; border-radius: 12px; background-color: #ffffff; box-shadow: 0px 10px 30px rgba(0, 0, 0, 0.1); transition: all 0.3s ease;">
        
        <!-- Email ile Doğrulama Formu -->
        <form action="${url.loginAction}" method="post" style="width: 100%; border-radius: 8px;">
            <input type="hidden" name="method" value="email">
            <button type="submit" style="width: 100%; padding: 18px; font-size: 18px; font-weight: 600; color: #fff; background-color: #1abc9c; border: none; border-radius: 8px; cursor: pointer; transition: all 0.3s ease; box-shadow: 0px 6px 20px rgba(26, 188, 156, 0.2);">
                E-posta ile Doğrulama
            </button>
        </form>
        
        <!-- Telefon ile Doğrulama Formu -->
        <form action="${url.loginAction}" method="post" style="width: 100%; border-radius: 8px;">
            <input type="hidden" name="method" value="phone">
            <button type="submit" style="width: 100%; padding: 18px; font-size: 18px; font-weight: 600; color: #fff; background-color: #3498db; border: none; border-radius: 8px; cursor: pointer; transition: all 0.3s ease; box-shadow: 0px 6px 20px rgba(52, 152, 219, 0.2);">
                Telefon ile Doğrulama
            </button>
        </form>
        
        <!-- Uygulama ile Doğrulama Formu -->
        <form action="${url.loginAction}" method="post" style="width: 100%; border-radius: 8px;">
            <input type="hidden" name="method" value="totp">
            <button type="submit" style="width: 100%; padding: 18px; font-size: 18px; font-weight: 600; color: #fff; background-color: #f39c12; border: none; border-radius: 8px; cursor: pointer; transition: all 0.3s ease; box-shadow: 0px 6px 20px rgba(243, 156, 18, 0.2);">
                Uygulama ile Doğrulama
            </button>
        </form>
    </div>

    <#-- Footer -->
    <div id="kc-footer">
        <div id="footer-content">
            <span>© 2024 Projem Kapısı. Tüm hakları saklıdır.</span>
            <a href="#">Gizlilik Politikası</a> |
            <a href="#">Kullanım Şartları</a> |
            <a href="#">İletişim</a>
        </div>
    </div>
</div>

<!-- Hover Effect Styles -->
<style>
    button {
        transition: all 0.3s ease;
        font-family: 'Helvetica Neue', sans-serif;
    }

    button:hover {
        background-color: #16a085; /* Koyu Yeşil */
        box-shadow: 0px 6px 30px rgba(26, 188, 156, 0.3);
        transform: translateY(-4px); /* Hafif Yukarı Kayma */
    }

    button:active {
        background-color: #2980b9; /* Koyu Mavi */
        box-shadow: 0px 4px 12px rgba(52, 152, 219, 0.3);
        transform: translateY(1px); /* Hafif Aşağı Kayma */
    }

    #kc-footer {
        width: 100%;
        background-color: #f1f1f1;
        text-align: center;
        font-size: 12px;
        color: #666;
        border-top: 1px solid #ddd;
        padding: 15px 0;
        position: fixed;
        bottom: 0;
        left: 0;
    }

    #footer-content {
        display: inline-flex;
        gap: 10px;
        color: #666;
    }

    #footer-content span {
        font-weight: 400;
    }

    #footer-content a {
        color: #007bff;
        text-decoration: none;
        margin: 0 5px;
    }

    #footer-content a:hover {
        text-decoration: underline;
    }

    /* Fade-in Geçiş Efekti */
    #main-content {
        opacity: 0;
        background-color: #ffffff;
        animation: fadeIn 0.6s ease forwards;
    }

    @keyframes fadeIn {
        0% {
            opacity: 0;
            background-color: #ffffff;
        }
        100% {
            opacity: 1;
            background-color: #ffffff;
        }
    }
</style>

<script>
    window.onload = () => {
        document.getElementById("main-content").style.opacity = "1";
    };
</script>
