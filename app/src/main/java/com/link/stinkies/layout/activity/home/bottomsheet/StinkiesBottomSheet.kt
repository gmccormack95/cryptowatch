package com.link.stinkies.layout.activity.home.bottomsheet

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.ClipboardManager
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import com.link.stinkies.model.biz.Post
import com.link.stinkies.model.biz.PostThread
import com.link.stinkies.viewmodel.activity.HomeActivityVM

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheet(viewModel: HomeActivityVM, onDownload: (Post?) -> Unit, onDismiss: () -> Unit,
                onShare: (PostThread?, Post?) -> Unit) {
    val modalBottomSheetState = rememberModalBottomSheetState()
    val clipboardManager: ClipboardManager = LocalClipboardManager.current
    val context = LocalContext.current
    val thread = viewModel.bottomsheetVM.thread.observeAsState()
    val post = viewModel.bottomsheetVM.post.observeAsState()

    ModalBottomSheet(
        onDismissRequest = { onDismiss() },
        sheetState = modalBottomSheetState,
        dragHandle = { BottomSheetDefaults.DragHandle() },
    ) {
        Column(
            modifier = Modifier
                .padding(bottom = 64.dp)
        ) {
            if(post.value?.imageUrl != null) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .clickable {
                            onDownload(post.value)
                            onDismiss()
                        }
                ) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "Download Image",
                        tint = Color.DarkGray,
                        modifier = Modifier
                            .size(28.dp)
                            .rotate(270f)
                            .padding(end = 8.dp, bottom = 8.dp)
                    )
                    Text(
                        "Download Image",
                        color = Color.DarkGray,
                        modifier = Modifier
                            .padding(top = 4.dp)
                    )
                }
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .clickable {
                        clipboardManager.setText(AnnotatedString(post.value?.comment ?: ""))
                        Toast
                            .makeText(
                                context,
                                "Copied to clipboard",
                                Toast.LENGTH_SHORT
                            )
                            .show()
                        onDismiss()
                    }
            ) {
                Icon(
                    imageVector = Icons.Filled.Edit,
                    contentDescription = "Copy Text",
                    tint = Color.DarkGray,
                    modifier = Modifier
                        .size(28.dp)
                        .padding(end = 8.dp)
                )
                Text(
                    "Copy Text",
                    color = Color.DarkGray,
                    modifier = Modifier
                        .padding(bottom = 2.dp)
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .clickable {
                        onShare(thread.value, post.value)
                        onDismiss()
                    }
            ) {
                Icon(
                    imageVector = Icons.Filled.Share,
                    contentDescription = "Share",
                    tint = Color.DarkGray,
                    modifier = Modifier
                        .size(28.dp)
                        .padding(end = 8.dp)
                )
                Text(
                    "Share Post",
                    color = Color.DarkGray,
                )
            }
        }
    }
}