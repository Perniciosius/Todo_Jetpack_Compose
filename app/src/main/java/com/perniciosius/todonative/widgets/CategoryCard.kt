package com.perniciosius.todonative.widgets

import android.app.Application
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.compositeOver
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.perniciosius.todonative.models.Category
import com.perniciosius.todonative.models.getCategoryColor
import com.perniciosius.todonative.models.getCategoryImage
import com.perniciosius.todonative.models.getCategoryName
import com.perniciosius.todonative.services.database.TodoViewModel
import com.perniciosius.todonative.services.database.TodoViewModelFactory

@Composable
fun CategoryCard(
    selectedCategory: Category?,
    todoViewModel: TodoViewModel = viewModel(factory = TodoViewModelFactory(
        LocalContext.current.applicationContext as Application
        )
    ),
    onSelectedCategoryChanged: (Category) -> Unit
) {
    val categories = Category.values()
    LazyRow {
        items(categories) {
                category -> Card(
            elevation = 10.dp,
            modifier = Modifier
                .padding(horizontal = 10.dp)
                .size(190.dp, 150.dp)
                .clip(MaterialTheme.shapes.medium)
                .clickable {
                    onSelectedCategoryChanged(category)
                }
        ) {
            val todoCount by todoViewModel.getCount(category).observeAsState(initial = 0)
            val todoCompletedCount by todoViewModel.getCompletedCount(category).observeAsState(
                initial = 0
            )
            val progress by animateFloatAsState(
                targetValue = todoCompletedCount.toFloat() / todoCount,
                animationSpec = tween(500)
            )
            Column(
                modifier = Modifier
                    .background(
                        color = if (selectedCategory != category)
                            MaterialTheme.colors.primaryVariant
                        else
                            MaterialTheme.colors.secondary
                                .copy(alpha = 0.3f)
                                .compositeOver(MaterialTheme.colors.primaryVariant)
                    )
                    .padding(15.dp),
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Image(
                        painter = painterResource(id = category.getCategoryImage()),
                        contentDescription = category.getCategoryName(),
                        modifier = Modifier.size(60.dp)
                    )
                    Text(text = "$todoCount tasks", style = MaterialTheme.typography.subtitle1)
                }
                Text(text = category.getCategoryName(), style = MaterialTheme.typography.h5)
                LinearProgressIndicator(
                    progress = progress,
                    color = category.getCategoryColor(),
                    modifier = Modifier.clip(RoundedCornerShape(20F))
                )
            }
        }
        }
    }
}