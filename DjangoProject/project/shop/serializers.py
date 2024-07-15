from rest_framework import serializers
from shop.models import Game,Steam,Epicgames,Gog
from django.contrib.auth.models import User
from rest_framework.response import Response
from rest_framework import status
from rest_framework.validators import UniqueValidator
from django.contrib.auth.password_validation import validate_password
from django.contrib.auth.models import Group

class GameSerializerList(serializers.ModelSerializer):
    gamegenre = serializers.CharField(source='gamegenre.typeofgenre')
    launchertype = serializers.CharField(source='launchertype.launchername')
    
    class Meta:
        model = Game
        fields = ['idgame','gamename','gamepublisher','gamedeveloper','releasedate',
                  'gamegenre','launchertype','gameprice']
        read_only_fields = ['idgame']

class GameSerializer(serializers.ModelSerializer):
    class Meta:
        model = Game
        fields = ['idgame','gamename','gamepublisher','gamedeveloper','releasedate',
                  'gamegenre','launchertype','gameprice']
        read_only_fields = ['idgame']


class SteamSerializer(serializers.ModelSerializer):
    game_name = serializers.ReadOnlyField(source='idgamesteam.gamename')
    class Meta:
        model = Steam
        fields = ['game_name','gameprice']
        read_only_fields = ['game_name','gameprice']

class EpicGamesSerializer(serializers.ModelSerializer):
    game_name = serializers.ReadOnlyField(source='idgameepicgames.gamename')
    class Meta:
        model = Epicgames
        fields = ['game_name','gameprice']
        read_only_fields = ['game_name','gameprice']

class GogSerializer(serializers.ModelSerializer):
    game_name = serializers.ReadOnlyField(source='idgamegog.gamename')
    class Meta:
        model = Gog
        fields = ['game_name','gameprice']
        read_only_fields = ['game_name','gameprice']


class UserSerializer(serializers.ModelSerializer):
  class Meta:
    model = User
    fields = ["id", "first_name", "last_name", "username","is_staff"]

class RegisterSerializer(serializers.ModelSerializer):
  email = serializers.EmailField(
    required=True,
    validators=[UniqueValidator(queryset=User.objects.all())]
  )
  password = serializers.CharField(
    write_only=True, required=True, validators=[validate_password])
  password2 = serializers.CharField(write_only=True, required=True)
  class Meta:
    model = User
    fields = ('username', 'password', 'password2',
         'email', 'first_name', 'last_name')
    extra_kwargs = {
      'first_name': {'required': True},
      'last_name': {'required': True}
    }
  def validate(self, attrs):
    if attrs['password'] != attrs['password2']:
      raise serializers.ValidationError(
        {"password": "Password fields didn't match."})
    return attrs
  def create(self, validated_data):
    user = User.objects.create(
      username=validated_data['username'],
      email=validated_data['email'],
      first_name=validated_data['first_name'],
      last_name=validated_data['last_name']
    )
    user.set_password(validated_data['password'])
    user.save()

    default_group = Group.objects.get(name='Users')  
    user.groups.add(default_group)
    return user