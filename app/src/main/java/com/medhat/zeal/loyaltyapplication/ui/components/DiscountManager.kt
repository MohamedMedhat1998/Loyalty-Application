package com.medhat.zeal.loyaltyapplication.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.medhat.zeal.loyaltyapplication.R
import com.medhat.zeal.loyaltyapplication.ui.theme.LoyaltyApplicationTheme

@Composable
fun DiscountManager(
    discount: String?,
    prefilledDiscountValue: String?,
    onSaveClicked: (discountValue: Float) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    var discountValue by rememberSaveable(prefilledDiscountValue) {
        mutableStateOf(
            prefilledDiscountValue.orEmpty()
        )
    }
    var errorText: String? by rememberSaveable { mutableStateOf(null) }

    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Text(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            text = stringResource(
                R.string.current_discount,
                discount ?: stringResource(R.string.no_discount)
            )
        )

        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = discountValue,
            onValueChange = {
                discountValue = it
                if (errorText != null) {
                    errorText = null
                }
            },
            isError = errorText != null,
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            placeholder = {
                Text(
                    modifier = Modifier.alpha(0.5f),
                    text = stringResource(R.string.enter_discount_value)
                )
            }
        )

        errorText?.let {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = it,
                color = MaterialTheme.colorScheme.error
            )
        }
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                val numericValue = discountValue.toFloatOrNull()
                if (numericValue != null) {
                    onSaveClicked(numericValue)
                } else {
                    errorText = context.getString(R.string.invalid_discount_value)
                }
            }
        ) {
            Text(text = stringResource(R.string.save))
        }
    }
}

@Preview
@Composable
private fun DiscountManagerPreview() {
    LoyaltyApplicationTheme {
        DiscountManager(
            modifier = Modifier.fillMaxWidth(),
            prefilledDiscountValue = "50",
            discount = "30",
            onSaveClicked = {}
        )
    }
}

@Preview
@Composable
private fun DiscountManagerNoPreDefinedValuePreview() {
    LoyaltyApplicationTheme {
        DiscountManager(
            modifier = Modifier.fillMaxWidth(),
            prefilledDiscountValue = null,
            discount = null,
            onSaveClicked = {}
        )
    }
}