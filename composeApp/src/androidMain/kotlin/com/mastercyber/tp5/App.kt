package com.mastercyber.tp5

import android.graphics.BitmapFactory
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    MaterialTheme {
        // État permettant d'afficher ou de masquer le contenu
        var showContent by remember { mutableStateOf(false) }

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Bouton pour déclencher l'apparition du contenu
            Button(onClick = { showContent = !showContent }) {
                Text("Click me!")
            }

            // Affiche le contenu uniquement quand showContent est à true
            AnimatedVisibility(visible = showContent) {
                var name by remember { mutableStateOf<String?>(null) }
                var imageByteArray by remember { mutableStateOf<ByteArray?>(null) }

                // Lorsqu'on passe showContent à true,
                // on récupère un Pokémon et son image
                LaunchedEffect(showContent) {
                    if (showContent) {
                        val (pokemonName, imageUrl) = Greeting().fetchPokemonWithImage()
                        name = pokemonName
                        imageByteArray = Greeting().fetchPokemonImage(imageUrl)
                    }
                }

                // Matrice pour convertir l'image en nuances de gris
                val grayscaleMatrix = floatArrayOf(
                    0.33f, 0.33f, 0.33f, 0f, 0f,
                    0.33f, 0.33f, 0.33f, 0f, 0f,
                    0.33f, 0.33f, 0.33f, 0f, 0f,
                    0f,    0f,    0f,    1f, 0f
                )
                val colorFilter = ColorFilter.colorMatrix(ColorMatrix(grayscaleMatrix))

                // Image et du nom du Pokémon
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    imageByteArray?.let { byteArray ->
                        val bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
                        bitmap?.let {
                            Image(
                                bitmap = it.asImageBitmap(),
                                contentDescription = "Pokémon Image",
                                modifier = Modifier,
                                colorFilter = colorFilter
                            )
                        }
                    }
                    Text(text = "Compose: ${name ?: "Chargement..."}")
                }
            }
        }
    }
}
