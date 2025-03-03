<h1 align="center">Products</h1>


# Screenshots
|<img src="screenshots/o1.png" width=200/>|<img src="screenshots/o2.png" width=200/>|

## Tech-stack

* Tech-stack
    * [Kotlin](https://kotlinlang.org/) - a cross-platform, statically typed, general-purpose programming language with type inference.
    * [Coroutines](https://kotlinlang.org/docs/reference/coroutines-overview.html) - perform background operations.
    * [Flow](https://kotlinlang.org/docs/reference/coroutines/flow.html) - handle the stream of data asynchronously that executes sequentially.
    * [HILT](https://dagger.dev/hilt/) - Hilt provides a standard way to incorporate Dagger dependency injection into an Android application.
    * [Jetpack Compose](https://developer.android.com/compose) -  Modern UI toolkit for building native Android UIs declaratively..
    * [Jetpack](https://developer.android.com/jetpack)
        * [Lifecycle](https://developer.android.com/topic/libraries/architecture/lifecycle) - perform action when lifecycle state changes.
        * [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) - store and manage UI-related data in a lifecycle conscious way.

* Architecture
    * MVVM - Model View View Model
* Tests
    * [Mocck](https://mockk.io/)

* Secure Data Handling
  * dependencies
    ```
    implementation("androidx.security:security-crypto:1.0.0") 
    implementation("net.zetetic:android-database-sqlcipher:4.5.0")
    ```
  *  Generate & Retrieve Encryption Key
        ```
        class UserDatabasePassphrase(context: Context) {
        private val keyFile = File(context.filesDir, "user_passphrase.bin")

        fun getPassphrase(): ByteArray {
            return if (keyFile.exists()) keyFile.readBytes() else generatePassphrase().also { keyFile.writeBytes(it) }
        }

        private fun generatePassphrase(): ByteArray = ByteArray(32).also { SecureRandom().nextBytes(it) }
        }
        ```

 * Secure Key Storage
        ```
            val masterKey = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)
            val encryptedFile = EncryptedFile.Builder(
                File(context.filesDir, "user_passphrase.bin"), context, masterKey,
                EncryptedFile.FileEncryptionScheme.AES256_GCM_HKDF_4KB
            ).build()
        ```
 * Provide Encrypted Room Database (Hilt)
    ```
    @Module
    @InstallIn(SingletonComponent::class)
    object DatabaseModule {
    @Provides
    fun providePassphrase(@ApplicationContext context: Context) = UserDatabasePassphrase(context)

    @Provides
    fun provideSupportFactory(passphrase: UserDatabasePassphrase) = SupportFactory(passphrase.getPassphrase())

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context, factory: SupportFactory): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, "secure.db")
            .openHelperFactory(factory)
            .build()
        }
    }
    ```




## [![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)
```
   Copyright 2025 Paul Mburu

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
   ```
