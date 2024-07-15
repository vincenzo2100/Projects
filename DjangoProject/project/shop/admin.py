from django.contrib import admin
from .models import Game,Gamegenre,Gamelauncher,Steam,Epicgames,Gog

class GameAdmin(admin.ModelAdmin):
    read_only_fields = ['launchertype']
    list_display = ['idgame','gamename','gamepublisher',
                    'gamedeveloper','releasedate','gamegenre','launchertype','gameprice']
    
class GamelauncherAdmin(admin.ModelAdmin):
    list_display = ['idgamelauncher','launchername']

class SteamAdmin(admin.ModelAdmin):
    readonly_fields = ['idgamesteam','gameprice']
    list_display = ['idgamesteam','gameprice']


class EpicGamesAdmin(admin.ModelAdmin):
    readonly_fields = ['idgameepicgames','gameprice']
    list_display = ['idgameepicgames','gameprice']
    

class GOGAdmin(admin.ModelAdmin):
    readonly_fields = ['idgamegog','gameprice']
    list_display = ['idgamegog','gameprice']

admin.site.register(Game,GameAdmin)
admin.site.register(Gamegenre)
admin.site.register(Gamelauncher,GamelauncherAdmin)
admin.site.register(Steam,SteamAdmin)
admin.site.register(Epicgames,EpicGamesAdmin)
admin.site.register(Gog,GOGAdmin)


