package com.jh.xysp.util;

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

public class EncryptPropertyPlaceholderConfigurer extends PropertyPlaceholderConfigurer{
	// ��Ҫ���ܵ��ֶ�����
    
	private String[] encryptPropNames = { "jdbc.username", "jdbc.password" };
    // �Թؼ������Խ���ת��
    @Override
    protected String convertProperty(String propertyName, String propertyValue) {
        if (isEncryptProp(propertyName)) {
            // ����
            String decryptValue = DESUtils.getDecryptString(propertyValue);
            return decryptValue;
        } else {
            return propertyValue;
        }
    }

	/**�жϸ������Ƿ����
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