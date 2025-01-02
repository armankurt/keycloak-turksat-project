<#import "template.ftl" as layout>

<div id="main-content" style="display: flex; flex-direction: column; align-items: center; justify-content: center; min-height: 100vh; font-family: 'Arial', sans-serif; background-color: #f9f9f9; color: #333;">
    <div style="text-align: center; margin-bottom: 20px;">
        <h1 style="font-size: 32px; font-weight: bold; margin: 0;">Projem Kapısı</h1>
        <p style="font-size: 16px; color: #555; margin-top: 8px;">Lütfen giriş yöntemini seçiniz.</p>
    </div>

<div class="selection-container">
    <#list auth.authenticationSelections as authenticationSelection>
        <form action="${url.loginAction}" method="post" style="width: 100%;">
            <input type="hidden" name="authenticationExecution" value="${authenticationSelection.authExecId}">
            <button type="submit" class="auth-button">
                <#switch authenticationSelection.displayName>
                    <#case "username-and-password-display-name">
                        ${msg(authenticationSelection.displayName)}
                        <#break>
                    <#case "tc-kimlik-authenticator-display-name">
                        TC Kimlik ile Giriş
                        <#break>
                    <#case "mobilsign-display-name">
                        Mobil İmza ile Giriş
                        <#break>
                    <#case "esign-display-name">
                        E-İmza ile Giriş
                        <#break>
                    <#default>
                        ${msg(authenticationSelection.displayName)}
                </#switch>
            </button>
        </form>
    </#list>
</div>

<style>
    /* Genel Stil */
    body {
        margin: 0;
        padding: 0;
        display: flex;
        align-items: center;
        justify-content: center;
        background-color: #f9f9f9;
    }

    .selection-container {
        display: flex;
        flex-direction: column;
        gap: 15px;
        width: 100%;
        max-width: 400px;
        padding: 20px;
        border-radius: 10px;
        background-color: #fff;
        box-shadow: 0px 4px 15px rgba(0, 0, 0, 0.1);
        transition: transform 0.3s, box-shadow 0.3s;
    }

    /* Buton Stili */
    .auth-button {
        width: 100%;
        padding: 16px; /* Eskiden 12px idi */
        font-size: 18px; /* Eskiden 16px idi */
        font-weight: 500;
        color: #fff;
        background-color: #007bff;
        border: none;
        border-radius: 8px; /* Hafif yuvarlatma artırıldı */
        cursor: pointer;
        transition: all 0.3s ease;
        box-shadow: 0px 3px 10px rgba(0, 123, 255, 0.3);
    }

    /* Buton Hover ve Focus Efektleri */
    .auth-button:hover {
        background-color: #0056b3;
        box-shadow: 0px 4px 12px rgba(0, 123, 255, 0.3); /* Hafif gölge */
        transform: scale(1.02); /* %5 büyütme */
        transition: transform 0.2s ease, background-color 0.2s ease, box-shadow 0.2s ease;
    }


    .auth-button:focus {
        outline: none;
        background-color: #004085;
        box-shadow: 0px 0px 0px 4px rgba(0, 123, 255, 0.3);
    }

    /* Buton Tıklama Efekti */
    .auth-button:active {
        background-color: #003366;
        transform: translateY(2px);
    }

    /* Fade In Animasyonu */
    #main-content {
        animation: fadeIn 0.4s ease-in-out;
    }

    @keyframes fadeIn {
        from {
            opacity: 0;
        }
        to {
            opacity: 1;
        }
    }
</style>

<script>
    window.onload = () => {
        document.getElementById("main-content").style.opacity = "1";
    };
</script>
