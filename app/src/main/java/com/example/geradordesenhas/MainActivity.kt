package com.example.geradordesenhas

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var edtSenha: EditText
    lateinit var botao: Button
    lateinit var txtSenhaGerada: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        edtSenha = findViewById(R.id.edtSenha)
        botao = findViewById(R.id.btnGerarSenha)
        txtSenhaGerada = findViewById(R.id.txtSenhaGerada)

        botao.setOnClickListener(object: View.OnClickListener{
            override fun onClick(v: View?) {
                var textoDigitado = edtSenha.text.toString()
                txtSenhaGerada.setText(encriptar(textoDigitado))
                Toast.makeText(applicationContext, "Gerando Senha", Toast.LENGTH_LONG).show()
            }
        })

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    private val primeiroPrimo: Int = 17;
    private val segundoPrimo:  Int = 29;
    private val caracteresEspeciais = arrayOf('!', '@', '?', '.', '#', '*', '(', ')')
    private val tamanhoMaximoDeCifra: Int = 6

    fun encriptar(textoEntrada: String): String{
        var tamanhoDaEntrada = textoEntrada.length;
        var numeroInicial: Int =  (tamanhoDaEntrada * primeiroPrimo);
        var numeroFinal: Int = (tamanhoDaEntrada * segundoPrimo);
        var caesar = caesarCipher(textoEntrada.takeLast(tamanhoMaximoDeCifra), tamanhoDaEntrada)
        var caesarCapitalizado = caesar.toLowerCase().capitalize()

        var indiceCaracterEspecial = tamanhoDaEntrada % caracteresEspeciais.size
        var caracterEspecial = caracteresEspeciais[indiceCaracterEspecial]
        return "$caracterEspecial$numeroInicial$caesarCapitalizado$numeroFinal$caracterEspecial";
    }

    fun caesarCipher(mensagem: String, shift: Int): String{
        val offset = shift % 26
        if (offset == 0) return mensagem;
        var caracterCifrado: Char;
        val caracteres = CharArray(mensagem.length)
        for((indice, caracter) in mensagem.withIndex()){
            if (caracter in 'A'..'Z'){
                caracterCifrado = caracter + offset
                if(caracterCifrado > 'Z') caracterCifrado -= 26
            }
            else if( caracter in 'a'..'z'){
                caracterCifrado = caracter + offset
                if(caracterCifrado > 'z') caracterCifrado -= 26
            }
            else caracterCifrado = caracter
            caracteres[indice] = caracterCifrado
        }
        return caracteres.joinToString("");
    }
}
