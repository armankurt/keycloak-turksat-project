Subject: Reset Your Password

Dear ${user.firstName},

You have requested to reset your password for your account on ${realmName}.

Click the link below to reset your password:
${link}

If you did not request this password reset, please ignore this email.

Best regards,
${realmName} Team
bash-5.1# cat login-error-notification.ftl 
Merhaba ${user.firstName},

Sistemimize yaptığınız giriş başarısız oldu. Lütfen şifrenizi kontrol edin ve tekrar deneyin.

Saygılarımızla,
${realmName} Ekibi