// build.gradle.kts
plugins {
    `maven-publish` // Chỉ cần plugin này
}

// Các giá trị này rất quan trọng
// JitPack sẽ dùng 'group' và 'version' này
group = "com.github.quyetnv9pay" // Thay bằng username của bạn
version = "1.0.0" // Phiên bản bạn muốn

publishing {
    publications {
        create<MavenPublication>("release") {
            // Tên artifactId sẽ là tên repo (ví dụ: my-library-aar)

            // Chỉ định artifact là file .arr trong thư mục libs
            artifact("libs/EncryptService.arr") {
                extension = "aar"
            }

            // !! QUAN TRỌNG !!
            // Bạn PHẢI khai báo các dependency mà file .arr của bạn cần
            // Vì file .arr không tự chứa thông tin này
            pom.withXml {
                val dependenciesNode = asNode().appendNode("dependencies")

                val dep4 = dependenciesNode.appendNode("dependency")
                dep4.appendNode("groupId", "org.bouncycastle")
                dep4.appendNode("artifactId", "bcprov-jdk15to18")
                dep4.appendNode("version", "1.72")
                dep4.appendNode("scope", "compile")

                // Xử lý "exclude" cho Bouncy Castle
                val exclusionsNode = dep4.appendNode("exclusions")
                val exclusion = exclusionsNode.appendNode("exclusion")
                exclusion.appendNode("groupId", "org.bouncycastle")
                exclusion.appendNode("artifactId", "bcprov-jdk15on")
            }
        }
    }
}