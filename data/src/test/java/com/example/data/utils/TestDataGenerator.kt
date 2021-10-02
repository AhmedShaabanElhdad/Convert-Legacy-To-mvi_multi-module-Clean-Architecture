package com.example.data.utils

import com.example.entity.Product
import com.example.entity.Profile

/**
 * Dummy data generator for tests
 */
class TestDataGenerator {

    companion object {
        fun generateProducts(): List<Product> {
            val item1 = Product(
                id = 196,
                name_ar = " ديب فريزر الكتروستار أفقي 320 لتر - ستانلس",
                deal_description = """رقم الموديل: LC320SST00\nالسعة اللترية: 320 لتر\nاللون: ستانلس\nكفاءة عالية في التبريد\nأنظمة متعددة تسمج بالتجميد أو التبريد فقط\nعزل تام لمنع دخول الرطوبة للحفاظ على الأطعمة أكبر فترة ممكنة \nالحفاظ على الأطعمة لمدة 6 ساعات عند انقطاع التيار الكهربائي\nأمان في كل شيء (متضمن أمان الباب)\nتصميم جذاب وعصري\nنظام إضاءة داخلي يضيء الفريزر بالكامل\nكفاءة تبريد فوق العادة تتحمد حرارة الجو.\nالارتفاع: 86.5 سم\nالعرض: 115 سم\nالعمق: 60 سم""",
                brand = "Electrostar",
                image = "https://cdn.halan.io/images/E-Commerce/Products/Screenshot2021-03-02163448_1.png",
                name_en = "Electrostar Deep Freezer Hz 320 Litres - Stainless ",
                price = 588,
                images = listOf(
                    "https://cdn.halan.io/images/E-Commerce/Products/b9QUIhpDpyCSBhu.png",
                    "https://cdn.halan.io/images/E-Commerce/Products/fe3zfaHRKoasvw5.png",
                    "https://cdn.halan.io/images/E-Commerce/Products/Qi0ERCVfBS2xPNo.jpg",
                    "https://cdn.halan.io/images/E-Commerce/Products/tzVso7fRi6e879K.png",
                    "https://cdn.halan.io/images/E-Commerce/Products/Screenshot2021-03-18101348_10.png",
                    "https://cdn.halan.io/images/E-Commerce/Products/KH690LN_20020266.png"
                )
            )

            val item2 = Product(
                id = 269,
                name_ar = "ثلاجة كريازي 16 قدم نوفروست توين تربو",
                deal_description = """
                    رقم الموديل: E470 NV/2 \nالسعة اللترية: 450 لتر\nعدد الأبواب: 2 باب\nلون الثلاجة: ذهبي\nعرض: 73 سم\nعمق: 69.3 سم\nارتفاع 171.5 سم\nكفاءة استهلاك الطاقة: B\nسعة الفريزر: 98 لتر\nخاصية نو فروست\nالأرفف: مصنعة من زجاج مضاد للكسر\nموتور يعمل في درجات حراراة قارية\n
                """.trimIndent(),
                brand = "Kiriazi",
                image = "https://cdn.halan.io/images/E-Commerce/Products/phiX8unFZomayPd.png",
                name_en = "Kiriazi Refrigerator Twin Turbo No Frost 16 feet E470 NV/2 G ",
                price = 516,
                images = listOf(
                    "https://cdn.halan.io/images/E-Commerce/Products/b9QUIhpDpyCSBhu.png",
                    "https://cdn.halan.io/images/E-Commerce/Products/fe3zfaHRKoasvw5.png",
                    "https://cdn.halan.io/images/E-Commerce/Products/Qi0ERCVfBS2xPNo.jpg",
                    "https://cdn.halan.io/images/E-Commerce/Products/tzVso7fRi6e879K.png",
                    "https://cdn.halan.io/images/E-Commerce/Products/Screenshot2021-03-18101348_10.png",
                    "https://cdn.halan.io/images/E-Commerce/Products/KH690LN_20020266.png"
                )
            )


            return listOf(item1, item2)
        }

        fun generateProfile(): Profile {
            return Profile(
                username = "ahmedshaaban",
                image = "https://i.picsum.photos/id/1062/5092/3395.jpg?hmac=o9m7qeU51uOLfXvepXcTrk2ZPiSBJEkiiOp-Qvxja-k",
                name = "معز نديم",
                email = "z5yrjx4dxfxkyd5@gmail.com",
                phone = "01910894829"
            )
        }
    }

}