package com.fifteenpuzzle

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LifecycleOwner
import com.fifteenpuzzle.databinding.MainActivityBinding
import com.fifteenpuzzle.ui.main.Position
import java.util.Collections.shuffle
import java.util.stream.IntStream
import kotlin.streams.toList

class MainActivity : AppCompatActivity(), LifecycleOwner {
    private lateinit var binding: MainActivityBinding
    private var matrixSize = 4
    private var buttonSize = 300
    private var tableOffsetX = 125;
    private var tableOffsetY = 400;
    private var buttons = mutableListOf<Button>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MainActivityBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        initGame()

    }

    private fun initGame() {
        val l1 = IntStream.range(0, matrixSize).toList().toMutableList()
        val l2 = IntStream.range(0, matrixSize).toList().toMutableList()
        shuffle(l1)
        shuffle(l2)
        l1.forEach { x ->
            l2.forEach { y ->
                val button = createButton(y.toFloat(), x.toFloat())
                buttons.add(button)
                binding.container.addView(button)
            }
        }
    }

    private fun moveButton(currentButton: Button) {
        Log.i("Current Pos", "x: ${currentButton.x}  y: ${currentButton.y}")
        val neighbours =
            Position(
                (currentButton.x - tableOffsetX) / buttonSize,
                (currentButton.y - tableOffsetY) / buttonSize
            ).neighbours(
                matrixSize
            )

        val button = binding.container.findViewWithTag<Button>("target")
        val x = (button.x - tableOffsetX) / buttonSize
        val y = (button.y - tableOffsetY) / buttonSize
        if (neighbours.contains(Position(x, y))) {
            Log.i("Move", "valid")
            Log.i("Next Pos", "x: ${button.x}  y: ${button.y}")

            val nextPC = Position(currentButton.x, currentButton.y) - Position(button.x, button.y)
            val nextPN = Position(button.x, button.y) - Position(currentButton.x, currentButton.y)
//            Log.i("Translate", "$nextPC")
//            Log.i("Translate", "$nextPN")
            currentButton.translationX = currentButton.x + nextPC.x
            currentButton.translationY = currentButton.y + nextPC.y
            button.translationX = button.x + nextPN.x
            button.translationY = button.y + nextPN.y

        } else {
            Log.i("Move", "invalid")
        }
        if (check()) {
            Toast.makeText(this, "Congratulations", Toast.LENGTH_SHORT).show()
        }
    }

    private fun check(): Boolean {
        var b = true
        val bttns = mutableListOf<Button>()
        IntStream.range(1, matrixSize * matrixSize + 1).forEach {
            val button = binding.container.findViewById<Button>(it)
            bttns += button
        }
        var index = 0
        IntStream.range(0, matrixSize).forEach { i ->
            IntStream.range(0, matrixSize).forEach { j ->
                if (bttns[index].x != j * buttonSize.toFloat() || bttns[index].y != i * buttonSize.toFloat()) {
                    b = false
                }
                index++
            }
        }
        return b
    }

    private fun createButton(x: Float, y: Float): Button {
        val button = Button(this)
        button.x = x * buttonSize + tableOffsetX
        button.y = y * buttonSize + tableOffsetY
        button.width = buttonSize
        button.height = buttonSize
        button.id = View.generateViewId()
        button.setOnClickListener {
            moveButton(button)
        }
        button.text = "${button.id}"
        button.background.setTint(Color.GREEN)
        if (button.id == matrixSize * matrixSize) {
            button.text = " "
            button.tag = "target"
            button.background.setTint(Color.WHITE)
        }
        return button
    }
}