package com.zone.plugin.utils

import java.security.MessageDigest

class Md5Utils {

    static String generateMD5(File file) {

        try {
            MessageDigest digest = MessageDigest.getInstance("MD5")
            file.withInputStream() { is ->
                byte[] buffer = new byte[8192]
                int read = 0
                while ((read = is.read(buffer)) > 0) {
                    digest.update(buffer, 0, read);
                }
            }
            byte[] md5sum = digest.digest()
            BigInteger bigInt = new BigInteger(1, md5sum)
            return bigInt.toString(16).padLeft(32, '0')
        } catch (Exception e) {
            e.printStackTrace()
            return ""
        }
    }
}