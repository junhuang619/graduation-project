package com.jh.xysp.util;

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

public class EncryptPropertyPlaceholderConfigurer extends PropertyPlaceholderConfigurer{
	// 需要加密的字段数组
    
	private String[] encryptPropNames = { "jdbc.username", "jdbc.password" };
    // 对关键的属性进行转换
    @Override
    protected String convertProperty(String propertyName, String propertyValue) {
        if (isEncryptProp(propertyName)) {
            // 解密
            String decryptValue = DESUtils.getDecryptString(propertyValue);
            return decryptValue;
        } else {
            return propertyValue;
        }
    }

	/**判断该属性是否加密
	 * @param propertyName
	 * @return
	 */
	private boolean isEncryptProp(String propertyName) {
		for (String encryptpropertyName : encryptPropNames) {
            if (encryptpropertyName.equals(propertyName))
                return true;
        }
		return false;
	}
}
