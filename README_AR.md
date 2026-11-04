# GS3 Tactical Companion — Android Prototype 0.2

تطبيق مرافق تجريبي لهاتف Android مع HONOR Watch GS 3 Firmware 15.0.0.504، دون HONOR Account.

- فتح Gadgetbridge.
- إرسال أوامر مزامنة النشاط وتصدير ZIP عبر Intent API.
- لا يعرض نبض القلب أو الخطوات أو البطارية بقيم وهمية.
- لا يقرأ قاعدة بيانات Gadgetbridge الخاصة.
- تحويل صورة Watch Face إلى حزمة HWT يتطلب أداة HONOR المتوافقة؛ الصورة ليست ملف تثبيت.

## البناء
يُبنى APK تلقائياً عبر GitHub Actions عند كل push إلى main. افتح تبويب Actions ثم نزّل Artifact باسم GS3-Tactical-Companion-APK. الناتج Debug APK تجريبي.
