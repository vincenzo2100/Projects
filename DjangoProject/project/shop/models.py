# This is an auto-generated Django model module.
# You'll have to do the following manually to clean this up:
#   * Rearrange models' order
#   * Make sure each model has one field with primary_key=True
#   * Make sure each ForeignKey and OneToOneField has `on_delete` set to the desired behavior
#   * Remove `managed = False` lines if you wish to allow Django to create, modify, and delete the table
# Feel free to rename the models, but don't rename db_table values or field names.
from django.db import models


class AuthGroup(models.Model):
    name = models.CharField(unique=True, max_length=150)

    class Meta:
        managed = False
        db_table = 'auth_group'


class AuthGroupPermissions(models.Model):
    id = models.BigAutoField(primary_key=True)
    group = models.ForeignKey(AuthGroup, models.DO_NOTHING)
    permission = models.ForeignKey('AuthPermission', models.DO_NOTHING)

    class Meta:
        managed = False
        db_table = 'auth_group_permissions'
        unique_together = (('group', 'permission'),)


class AuthPermission(models.Model):
    name = models.CharField(max_length=255)
    content_type = models.ForeignKey('DjangoContentType', models.DO_NOTHING)
    codename = models.CharField(max_length=100)

    class Meta:
        managed = False
        db_table = 'auth_permission'
        unique_together = (('content_type', 'codename'),)


class AuthUser(models.Model):
    password = models.CharField(max_length=128)
    last_login = models.DateTimeField(blank=True, null=True)
    is_superuser = models.IntegerField()
    username = models.CharField(unique=True, max_length=150)
    first_name = models.CharField(max_length=150)
    last_name = models.CharField(max_length=150)
    email = models.CharField(max_length=254)
    is_staff = models.IntegerField()
    is_active = models.IntegerField()
    date_joined = models.DateTimeField()

    class Meta:
        managed = False
        db_table = 'auth_user'


class AuthUserGroups(models.Model):
    id = models.BigAutoField(primary_key=True)
    user = models.ForeignKey(AuthUser, models.DO_NOTHING)
    group = models.ForeignKey(AuthGroup, models.DO_NOTHING)

    class Meta:
        managed = False
        db_table = 'auth_user_groups'
        unique_together = (('user', 'group'),)


class AuthUserUserPermissions(models.Model):
    id = models.BigAutoField(primary_key=True)
    user = models.ForeignKey(AuthUser, models.DO_NOTHING)
    permission = models.ForeignKey(AuthPermission, models.DO_NOTHING)

    class Meta:
        managed = False
        db_table = 'auth_user_user_permissions'
        unique_together = (('user', 'permission'),)


class DjangoAdminLog(models.Model):
    action_time = models.DateTimeField()
    object_id = models.TextField(blank=True, null=True)
    object_repr = models.CharField(max_length=200)
    action_flag = models.PositiveSmallIntegerField()
    change_message = models.TextField()
    content_type = models.ForeignKey('DjangoContentType', models.DO_NOTHING, blank=True, null=True)
    user = models.ForeignKey(AuthUser, models.DO_NOTHING)

    class Meta:
        managed = False
        db_table = 'django_admin_log'


class DjangoContentType(models.Model):
    app_label = models.CharField(max_length=100)
    model = models.CharField(max_length=100)

    class Meta:
        managed = False
        db_table = 'django_content_type'
        unique_together = (('app_label', 'model'),)


class DjangoMigrations(models.Model):
    id = models.BigAutoField(primary_key=True)
    app = models.CharField(max_length=255)
    name = models.CharField(max_length=255)
    applied = models.DateTimeField()

    class Meta:
        managed = False
        db_table = 'django_migrations'


class DjangoSession(models.Model):
    session_key = models.CharField(primary_key=True, max_length=40)
    session_data = models.TextField()
    expire_date = models.DateTimeField()

    class Meta:
        managed = False
        db_table = 'django_session'


class Game(models.Model):
    idgame = models.AutoField(db_column='IDGame', primary_key=True)  # Field name made lowercase.
    gamename = models.CharField(db_column='GameName', max_length=45, blank=False, null=False)  # Field name made lowercase.
    gamepublisher = models.CharField(db_column='GamePublisher', max_length=45, blank=True, null=True)  # Field name made lowercase.
    gamedeveloper = models.CharField(db_column='GameDeveloper', max_length=45, blank=False, null=False)  # Field name made lowercase.
    releasedate = models.DateField(db_column='ReleaseDate', blank=True, null=True)  # Field name made lowercase.
    gamegenre = models.ForeignKey('Gamegenre', on_delete=models.DO_NOTHING, db_column='GameGenre', blank=False, null=False)  # Field name made lowercase.
    launchertype = models.ForeignKey('Gamelauncher', on_delete=models.DO_NOTHING, db_column='LauncherType', blank=False, null=False)  # Field name made lowercase.
    gameprice = models.FloatField(db_column='GamePrice', blank=False, null=False)  # Field name made lowercase.

    class Meta:
        managed = False
        db_table = 'game'
        verbose_name_plural = 'Game'

    def __str__(self):
        return f"{self.gamename}"

class Gamegenre(models.Model):
    idgenre = models.AutoField(db_column='IDGenre', primary_key=True)  # Field name made lowercase.
    typeofgenre = models.CharField(db_column='TypeOfGenre', max_length=45, blank=True, null=True)  # Field name made lowercase.

    class Meta:
        managed = False
        db_table = 'gamegenre'
        verbose_name_plural = 'GameGenre'

    def __str__(self):
        return f"{self.typeofgenre}"


class Gamelauncher(models.Model):
    idgamelauncher = models.IntegerField(db_column='IDGameLauncher', primary_key=True)  # Field name made lowercase.
    launchername = models.CharField(db_column='LauncherName', max_length=45, blank=True, null=True)  # Field name made lowercase.

    class Meta:
        managed = False
        db_table = 'gamelauncher'
        verbose_name_plural = 'GameLauncher'

    def __str__(self):
        return f"{self.launchername}"
    
class Steam(models.Model):
    idgamesteam = models.OneToOneField(Game, on_delete=models.CASCADE, db_column='IDGameSteam', primary_key=True)  # Field name made lowercase.
    gameprice = models.FloatField(db_column='GamePrice', blank=True, null=True)  # Field name made lowercase.

    class Meta:
        managed = False
        db_table = 'steam'
        verbose_name_plural = 'Steam'

    def __str__(self):
        return f"{self.idgamesteam}"
    
class Epicgames(models.Model):
    idgameepicgames = models.OneToOneField('Game', on_delete=models.CASCADE, db_column='IDGameEpicGames', primary_key=True)  # Field name made lowercase.
    gameprice = models.FloatField(db_column='GamePrice', blank=True, null=True)  # Field name made lowercase.

    class Meta:
        managed = False
        db_table = 'epicgames'
        verbose_name_plural = 'EpicGames'

    def __str__(self):
        return f"{self.idgameepicgames}"
    
class Gog(models.Model):
    idgamegog = models.OneToOneField(Game, on_delete=models.CASCADE, db_column='IDGameGOG', primary_key=True)  # Field name made lowercase.
    gameprice = models.FloatField(db_column='GamePrice', blank=True, null=True)  # Field name made lowercase.

    class Meta:
        managed = False
        db_table = 'gog'
        verbose_name_plural = 'GOG'

    def __str__(self):
        return f"{self.idgamegog}"