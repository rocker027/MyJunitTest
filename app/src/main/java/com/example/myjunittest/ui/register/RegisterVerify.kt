package com.example.myjunittest.ui.register

class RegisterVerify {
    fun verityUserId(userId: String): Boolean {
        return when {
            verifyUserIdFormat(userId) -> true
            else -> false
        }
    }

    fun verifyUserPassword(userPassword: String): Boolean {
        return when {
            verifyPasswordFormat(userPassword) -> true
            else -> false
        }
    }

    /**
     * register businessLogic function
     * @param userId (String) user id
     * @param userPassword (String) user password
     * @return (Boolean)
     */
    fun verifyRegisterInfo(userId: String, userPassword: String): Boolean {
        return verityUserId(userId) && verifyUserPassword(userPassword)
    }

    /**
     * verify password format
     * @param userPassword (String) user password
     * @return (Boolean) verify result
     */
    private fun verifyPasswordFormat(userPassword: String): Boolean {
        return verifyLength(userPassword) &&
                isLatterOfFirst(userPassword) &&
                hasDigit(userPassword)
    }

    /**
     * verify length
     * must one digit or more
     * @param userPassword (String) user password
     * @return (Boolean) verify result
     */
    private fun hasDigit(userPassword: String) = userPassword.any { it.isDigit() }

    /**
     * verify length
     * length must 8
     * @param userPassword (String) user password
     * @return (Boolean) verify result
     */
    private fun verifyLength(userPassword: String) = userPassword.length >= 8

    /**
     * verify password format
     * @param userId (String) user id
     * @return (Boolean) verify result
     */
    private fun verifyUserIdFormat(userId: String) = userId.length >= 6 &&
            isLatterOfFirst(userId)

    /**
     * verify first word is latter
     * @param userId (String) user id
     * @return (Boolean) verify result
     */
    private fun isLatterOfFirst(userId: String) = (userId.toUpperCase().first() in 'A'..'Z' ||
            userId.toUpperCase().first() in 'a'..'z')
}