package com.example.gs3tactical

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private const val GB = "nodomain.freeyourgadget.gadgetbridge"
private const val SYNC = "nodomain.freeyourgadget.gadgetbridge.command.ACTIVITY_SYNC"
private const val EXPORT = "nodomain.freeyourgadget.gadgetbridge.command.TRIGGER_ZIP_EXPORT"
private val Gold = Color(0xFFD6A84B)
private val Panel = Color(0xFF1B1D1F)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { Screen(
            launch = { packageManager.getLaunchIntentForPackage(GB)?.let(::startActivity) },
            sync = { sendBroadcast(Intent(SYNC).setPackage(GB)) },
            export = { sendBroadcast(Intent(EXPORT).setPackage(GB)) }
        ) }
    }
}

@Composable
private fun Screen(launch: () -> Unit, sync: () -> Unit, export: () -> Unit) {
    var tab by remember { mutableIntStateOf(0) }
    MaterialTheme(colorScheme = darkColorScheme(primary = Gold, background = Color(0xFF101112), surface = Panel)) {
        Scaffold(containerColor = Color(0xFF101112), bottomBar = {
            NavigationBar(containerColor = Panel) {
                NavigationBarItem(tab == 0, { tab = 0 }, icon = { Icon(Icons.Default.Watch, null) }, label = { Text("Dashboard") })
                NavigationBarItem(tab == 1, { tab = 1 }, icon = { Icon(Icons.Default.Palette, null) }, label = { Text("Watch Faces") })
                NavigationBarItem(tab == 2, { tab = 2 }, icon = { Icon(Icons.Default.Settings, null) }, label = { Text("Setup") })
            }
        }) { padding ->
            Column(Modifier.fillMaxSize().padding(padding).verticalScroll(rememberScrollState()).padding(20.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
                Text("GS3 TACTICAL", color = Gold, fontWeight = FontWeight.Bold, letterSpacing = 2.sp)
                when (tab) {
                    0 -> {
                        Text("Companion", color = Color.White, fontSize = 30.sp, fontWeight = FontWeight.Bold)
                        Card(colors = CardDefaults.cardColors(containerColor = Panel)) {
                            Column(Modifier.padding(18.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                                Text("HONOR Watch GS 3", color = Color.White, fontWeight = FontWeight.Bold)
                                Text("Firmware 15.0.0.504", color = Gold)
                                Text("الاتصال يُدار من Gadgetbridge", color = Color.LightGray)
                            }
                        }
                        Text("HEALTH DATA", color = Gold, fontWeight = FontWeight.Bold)
                        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                            Metric("HEART RATE", "— bpm", Modifier.weight(1f))
                            Metric("STEPS", "—", Modifier.weight(1f))
                        }
                        Metric("WATCH BATTERY", "— %", Modifier.fillMaxWidth())
                        Text("لن نعرض قيماً صحية غير متاحة من مصدر موثوق.", color = Color.LightGray, fontSize = 12.sp)
                        Button(onClick = launch, modifier = Modifier.fillMaxWidth()) { Icon(Icons.Default.OpenInNew, null); Spacer(Modifier.width(8.dp)); Text("فتح Gadgetbridge") }
                        Button(onClick = sync, modifier = Modifier.fillMaxWidth()) { Icon(Icons.Default.Sync, null); Spacer(Modifier.width(8.dp)); Text("مزامنة بيانات الساعة") }
                        OutlinedButton(onClick = export, modifier = Modifier.fillMaxWidth()) { Icon(Icons.Default.Archive, null); Spacer(Modifier.width(8.dp)); Text("طلب تصدير نسخة ZIP") }
                    }
                    1 -> {
                        Text("WATCH FACE LAB", color = Gold, fontWeight = FontWeight.Bold)
                        Text("Tactical Black & Gold", color = Color.White, fontSize = 24.sp)
                        Text("تصميم الوجه متاح كصورة معاينة فقط. تحويله إلى حزمة GS 3 يتطلب أداة HONOR المتوافقة.", color = Color.LightGray)
                    }
                    else -> {
                        Text("INTEGRATION SETUP", color = Gold, fontWeight = FontWeight.Bold)
                        Text("في Gadgetbridge افتح Settings ثم Developer options وفعّل Intent API. بعد ذلك جرّب أوامر المزامنة والتصدير من Dashboard.", color = Color.White)
                        Text("التطبيق لا يحتاج HONOR Account ولا يقرأ قاعدة بيانات Gadgetbridge الخاصة.", color = Color.LightGray)
                    }
                }
            }
        }
    }
}

@Composable
private fun Metric(label: String, value: String, modifier: Modifier) {
    Card(modifier, colors = CardDefaults.cardColors(containerColor = Panel)) {
        Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(label, color = Gold, fontSize = 10.sp, fontWeight = FontWeight.Bold)
            Text(value, color = Color.White, fontSize = 22.sp, fontWeight = FontWeight.Bold)
        }
    }
}
