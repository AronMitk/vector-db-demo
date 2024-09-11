package com.harrontech.vector_db_demo

import com.harrontech.vector_db_demo.dto.GetProductResponse
import com.harrontech.vector_db_demo.dto.CreateProductRequest
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("api/admin")
class ProductsConfigController(val controllers: List<ProductsController>) {
    @PostMapping("{version}")
    fun create(@PathVariable version: ControllerVersion): List<GetProductResponse> {
        val controller = controllers.first { it.getVersion() == version }

        val foods = listOf(
            CreateProductRequest("Apple", "A sweet, edible fruit that is one of the most popular fruits worldwide.", "Fruit"),
            CreateProductRequest("Banana", "A long, curved fruit that grows in clusters and has soft pulpy flesh.", "Fruit"),
            CreateProductRequest("Carrot", "A root vegetable that is often claimed to be the perfect health food. It is crunchy, tasty and highly nutritious.", "Vegetable"),
            CreateProductRequest("Date", "A sweet, dark brown oval fruit that is a great source of fiber, rich in vitamins and minerals.", "Fruit"),
            CreateProductRequest("Eggplant", "Also known as aubergine or brinjal, it's a purple, egg-shaped vegetable which is used in cuisines worldwide.", "Vegetable"),
            CreateProductRequest("Fig", "A sweet, pear-shaped fruit that is full of small seeds and often eaten dried.", "Fruit"),
            CreateProductRequest("Grapes", "A small, round green or purple fruit that is a good source of antioxidants, vitamin C and potassium.", "Fruit"),
            CreateProductRequest("Honeydew", "A sweet, juicy melon that has a sweet, slightly tangy flavor.", "Fruit"),
            CreateProductRequest("Iceberg Lettuce", "A round type of lettuce known for its mild flavor and firm, crunchy texture.", "Vegetable"),
            CreateProductRequest("Jackfruit", "A large, tropical fruit with a spiky rind. The flesh is yellow and sweet, often used as a meat substitute in vegetarian cuisine.", "Fruit"),
            CreateProductRequest("Kale", "A type of cabbage with green or purple leaves. It is among the most nutrient dense foods on the planet.", "Vegetable"),
            CreateProductRequest("Lemon", "A yellow, sour fruit used for culinary and non-culinary purposes throughout the world.", "Fruit"),
            CreateProductRequest("Mango", "Known as the king of the fruits, it is a tropical fruit with sweet, yellow flesh.", "Fruit"),
            CreateProductRequest("Nectarine", "A smooth-skinned variety of peach. It is a delicious summer fruit.", "Fruit"),
            CreateProductRequest("Orange", "Known for its tangy sweet taste, it is a citrus fruit that is a good source of Vitamin C.", "Fruit"),
            CreateProductRequest("Peach", "A round, sweet fruit with fuzzy, pinkish-yellow skin. It's also a good source of vitamins A and C.", "Fruit"),
            CreateProductRequest("Quince", "A yellow, hard fruit used in cooking to make jams, jellies and preserves.", "Fruit"),
            CreateProductRequest("Raspberry", "A small, red, sweet fruit often used in jams and desserts. It is high in fiber, vitamin C and manganese.", "Fruit"),
            CreateProductRequest("Strawberry", "A sweet, red fruit with a seed-studded surface. It is highly popular for its delicious taste and for its nutritional benefits.", "Fruit"),
            CreateProductRequest("Tomato", "A juicy, red fruit often used in salads and sauces. It is rich in vitamin C, potassium, folate, and vitamin K.", "Vegetable")
        )

        return foods.map { controller.create(it) }
    }

    @DeleteMapping("{version}")
    fun deleteAll(@PathVariable version: ControllerVersion) {
        controllers.first { it.getVersion() == version }.deleteAll()
    }

    @PutMapping("{version}")
    fun restartAll(@PathVariable version: ControllerVersion) {
        deleteAll(version)
        create(version)
    }
}