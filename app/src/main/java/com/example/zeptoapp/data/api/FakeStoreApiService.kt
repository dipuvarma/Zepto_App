package com.example.zeptoapp.data.api

import android.util.Log
import com.example.zeptoapp.data.model.Product
import com.example.zeptoapp.data.model.Rating
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okio.IOException
import org.json.JSONArray
import java.net.HttpURLConnection
import java.net.URL
import javax.net.ssl.HttpsURLConnection

class FakeStoreApiService {

    private val baseUrl = "https://fakestoreapi.com"

    /**
     * Fetches all products from FakeStore API.
     */
    suspend fun fetchProducts(): List<Product> = withContext(Dispatchers.IO) {
        val url = URL("$baseUrl/products")
        val connection = url.openConnection() as HttpsURLConnection
        return@withContext try {
            connection.connectTimeout = 10000
            connection.readTimeout = 10000
            connection.requestMethod = "GET"

            Log.d("FakeStoreApi", "Fetching products from $url")

            if (connection.responseCode == HttpsURLConnection.HTTP_OK) {
                val response = connection.inputStream.bufferedReader().use { it.readText() }
                Log.d("FakeStoreApi", "Product response received successfully")
                parseProductResponse(response)
            } else {
                Log.e("FakeStoreApi", "Failed to fetch products. Response code: ${connection.responseCode}")
                emptyList()
            }
        } catch (e: Exception) {
            Log.e("FakeStoreApi", "Error fetching products", e)
            emptyList()
        } finally {
            connection.disconnect()
            Log.d("FakeStoreApi", "Connection closed")
        }
    }

    /**
     * Parses JSON response into a list of Product objects.
     */
    private fun parseProductResponse(response: String): List<Product> {
        val products = mutableListOf<Product>()
        val jsonArray = JSONArray(response)

        Log.d("FakeStoreApi", "Parsing ${jsonArray.length()} products")

        for (i in 0 until jsonArray.length()) {
            val jsonObject = jsonArray.getJSONObject(i)

            // Extract product details from JSON
            val id = jsonObject.getInt("id")
            val title = jsonObject.getString("title")
            val price = jsonObject.getDouble("price")
            val imageUrl = jsonObject.getString("image")
            val category = jsonObject.getString("category")
            val description = jsonObject.getString("description")
            val rating = jsonObject.getJSONObject("rating")
            val rate = rating.getDouble("rate")
            val count = rating.getInt("count")

            // Add to product list
            products.add(
                Product(
                    id = id,
                    name = title,
                    price = price,
                    imageUrl = imageUrl,
                    category = category,
                    description = description,
                    rating = Rating(
                        review = rate,
                        count = count
                    )
                )
            )
        }

        Log.d("FakeStoreApi", "Parsed ${products.size} products")
        return products
    }

    /**
     * Fetches available product categories from the API.
     */
    suspend fun fetchCategories(): List<String> = withContext(Dispatchers.IO) {
        val url = URL("$baseUrl/products/categories")
        val connection = url.openConnection() as HttpsURLConnection

        return@withContext try {
            connection.connectTimeout = 10000
            connection.readTimeout = 10000
            connection.requestMethod = "GET"

            Log.d("FakeStoreApi", "Fetching categories from $url")

            val connectionCode = connection.responseCode
            if (connectionCode == HttpURLConnection.HTTP_OK) {
                val response = connection.inputStream.bufferedReader().use { it.readText() }
                Log.d("FakeStoreApi", "Category response received successfully")
                parseCategoriesResponse(response)
            } else {
                Log.e("FakeStoreApi", "Failed to fetch categories. Code: $connectionCode")
                emptyList()
            }
        } catch (e: IOException) {
            Log.e("FakeStoreApi", "Connection failed while fetching categories", e)
            emptyList()
        } finally {
            connection.disconnect()
            Log.d("FakeStoreApi", "Connection closed for categories")
        }
    }

    /**
     * Parses JSON response into a list of category strings.
     */
    private fun parseCategoriesResponse(response: String): List<String> {
        val categories = mutableListOf<String>()
        val jsonArray = JSONArray(response)

        Log.d("FakeStoreApi", "Parsing ${jsonArray.length()} categories")

        for (i in 0 until jsonArray.length()) {
            categories.add(jsonArray.getString(i))
        }

        Log.d("FakeStoreApi", "Parsed ${categories.size} categories")
        return categories
    }

    /**
     * Fetches products by specific category.
     */
    suspend fun fetchProductByCategory(category: String): List<Product> =
        withContext(Dispatchers.IO) {
            val url = URL("$baseUrl/products/category/$category")
            val connection = url.openConnection() as HttpsURLConnection

            return@withContext try {
                connection.connectTimeout = 10000
                connection.readTimeout = 10000
                connection.requestMethod = "GET"

                Log.d("FakeStoreApi", "Fetching products for category: $category")

                val connectionCode = connection.responseCode
                if (connectionCode == HttpURLConnection.HTTP_OK) {
                    val response = connection.inputStream.bufferedReader().use { it.readText() }
                    Log.d("FakeStoreApi", "Products by category received successfully")
                    parseProductResponse(response)
                } else {
                    Log.e("FakeStoreApi", "Failed to fetch category products. Code: $connectionCode")
                    emptyList()
                }
            } catch (e: IOException) {
                Log.e("FakeStoreApi", "Failed to connect for category: $category", e)
                emptyList()
            } finally {
                connection.disconnect()
                Log.d("FakeStoreApi", "Connection closed for category: $category")
            }
        }
}

