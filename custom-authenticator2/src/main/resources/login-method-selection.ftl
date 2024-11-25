<div style="text-align: center; padding: 20px;">
    <h1>İki Adımlı Doğrulama Seçimi</h1>
    <p>Lütfen doğrulama yöntemini seçiniz:</p>

    <form action="${url.loginAction}" method="post" style="display: flex; flex-direction: column; gap: 15px;">
        <button name="method" value="email" type="submit" style="padding: 10px 20px; background-color: #4CAF50; color: white; border: none; border-radius: 5px; font-size: 16px;">Email ile Doğrulama</button>
        <button name="method" value="phone" type="submit" style="padding: 10px 20px; background-color: #2196F3; color: white; border: none; border-radius: 5px; font-size: 16px;">Telefon ile Doğrulama</button>
        <button name="method" value="application" type="submit" style="padding: 10px 20px; background-color: #FF9800; color: white; border: none; border-radius: 5px; font-size: 16px;">Uygulama ile Doğrulama</button>
    </form>
</div>
