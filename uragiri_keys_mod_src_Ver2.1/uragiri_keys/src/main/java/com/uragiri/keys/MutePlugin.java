package com.uragiri.keys;

import de.maxhenkel.voicechat.api.VoicechatConnection;
import de.maxhenkel.voicechat.api.VoicechatPlugin;
import de.maxhenkel.voicechat.api.events.EventRegistration;
import de.maxhenkel.voicechat.api.events.MicrophonePacketEvent;
import net.minecraft.server.network.ServerPlayerEntity;

/**
 * Simple Voice Chat 用アドオン。ホスト(サーバー側)で動作し、
 * タグ "svc_mute" が付いたプレイヤーの音声を破棄(ミュート)する。
 */
public class MutePlugin implements VoicechatPlugin {
    @Override
    public String getPluginId() {
        return "uragiri_keys";
    }

    @Override
    public void registerEvents(EventRegistration registration) {
        registration.registerEvent(MicrophonePacketEvent.class, this::onMicrophone);
    }

    private void onMicrophone(MicrophonePacketEvent event) {
        VoicechatConnection connection = event.getSenderConnection();
        if (connection == null) {
            return;
        }
        Object player = connection.getPlayer().getPlayer();
        if (player instanceof ServerPlayerEntity serverPlayer
                && serverPlayer.getCommandTags().contains("svc_mute")) {
            event.cancel();
        }
    }
}
