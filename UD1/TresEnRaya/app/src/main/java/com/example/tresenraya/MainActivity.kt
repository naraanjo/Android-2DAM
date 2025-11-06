package com.example.tresenraya

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.tresenraya.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val board = Array(3) { IntArray(3) }
    private lateinit var boardCells: Array<Array<ImageView>>

    private var activePlayer = 1
    private var gameActive = false
    private var player1Score = 0
    private var player2Score = 0
    private var partidasObjetivo = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        boardCells = arrayOf(
            arrayOf(binding.img00, binding.img01, binding.img02), //Fila-0
            arrayOf(binding.img10, binding.img11, binding.img12), // Fila-1
            arrayOf(binding.img20, binding.img21, binding.img22) // Fila-2
        )

        configurarListeners()
        actualizarEstadoTurno()
    }

    private fun configurarListeners() {
        // Recorro  todas las casilla del tablero
        boardCells.forEachIndexed { fila, celdas ->
            celdas.forEachIndexed { columna, imageView ->
                imageView.setOnClickListener {
                    onCeldaPulsada(fila, columna)
                }
            }
        }

        binding.btnPlay.setOnClickListener {
            val valor = binding.etWinLimit.text.toString().toIntOrNull()
            if (valor == null || valor <= 0) {
                mostrarDialogo("Configuración incorrecta", "Introduce un número válido de partidas antes de jugar.")
            } else {
                partidasObjetivo = valor
                gameActive = true
                reiniciarTablero()
                Toast.makeText(this, "¡Comienza el juego a $valor victorias!", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnResetScores.setOnClickListener {
            reiniciarMatch()
        }
    }

    private fun onCeldaPulsada(fila: Int, columna: Int) {
        if (!gameActive) {
            mostrarDialogo("Juego inactivo", "Pulsa el botón 'Jugar' antes de empezar.")
            return
        }

        // Cero == casilla ocupada
        // Si no es cero ya esta ocupada - se sale
        if (board[fila][columna] != 0) return

        board[fila][columna] = activePlayer
        val imagenParaPoner = if (activePlayer == 1) R.drawable.monstruo1 else R.drawable.monstruo2
        boardCells[fila][columna].setImageResource(imagenParaPoner)

        if (comprobarVictoria()) {
            gestionarVictoria()
        } else if (esEmpate()) {
            gestionarEmpate()
        } else {
            cambiarJugador()
        }
    }

    private fun comprobarVictoria(): Boolean {

        // Comprobar filas
        for (i in 0..2) if (board[i][0]==activePlayer && board[i][1]==activePlayer && board[i][2]==activePlayer) return true
        for (i in 0..2) if (board[0][i]==activePlayer && board[1][i]==activePlayer && board[2][i]==activePlayer) return true

        // Columnas y diagonales
        if (board[0][0]==activePlayer && board[1][1]==activePlayer && board[2][2]==activePlayer) return true
        if (board[0][2]==activePlayer && board[1][1]==activePlayer && board[2][0]==activePlayer) return true
        return false
    }

    private fun esEmpate(): Boolean {
        // Recorre fila --> celda --> celda !=0
        // Tablero lleno = empate
        return board.all { fila -> fila.all { celda -> celda != 0 } }
    }
    private fun gestionarVictoria() {
        gameActive = false
        val nombreGanador = "Jugador $activePlayer"
        Toast.makeText(this, "¡$nombreGanador gana la partida!", Toast.LENGTH_LONG).show()

        if (activePlayer == 1) {
            player1Score++
            binding.tvScorePlayer1.text = player1Score.toString()
        } else {
            player2Score++
            binding.tvScorePlayer2.text = player2Score.toString()
        }

        if (player1Score >= partidasObjetivo || player2Score >= partidasObjetivo) {
            mostrarDialogo("¡Match terminado!", "$nombreGanador ha ganado el match.")
        } else {
            Handler(Looper.getMainLooper()).postDelayed({
                cambiarJugador()
                reiniciarTablero()
            }, 1500)
        }
    }

    private fun gestionarEmpate() {
        gameActive = false
        Toast.makeText(this, "¡Empate!", Toast.LENGTH_LONG).show()
        Handler(Looper.getMainLooper()).postDelayed({
            cambiarJugador()
            reiniciarTablero()
        }, 1500)
    }

    private fun cambiarJugador() {
        activePlayer = if (activePlayer == 1) 2 else 1
        actualizarEstadoTurno()
    }

    private fun actualizarEstadoTurno() {
        val imagenTurno = if (activePlayer == 1) R.drawable.monstruo1 else R.drawable.monstruo2
        binding.imgTurnPlayer.setImageResource(imagenTurno)
    }

    private fun reiniciarTablero() {

        gameActive = true
        actualizarEstadoTurno()
    }

    private fun reiniciarMatch() {
        player1Score = 0
        player2Score = 0
        binding.tvScorePlayer1.text = "0"
        binding.tvScorePlayer2.text = "0"
        partidasObjetivo = 0
        gameActive = false
        for (i in 0..2) for (j in 0..2) {
            board[i][j] = 0
            boardCells[i][j].setImageResource(0)
        }
        actualizarEstadoTurno()
        Toast.makeText(this, "Match Reiniciado. Configura un nuevo número y pulsa Jugar.", Toast.LENGTH_SHORT).show()
    }

    private fun mostrarDialogo(titulo: String, mensaje: String) {
        AlertDialog.Builder(this)
            .setTitle(titulo)
            .setMessage(mensaje)
            .setPositiveButton("OK", null)
            .show()
    }
}
