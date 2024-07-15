from django.shortcuts import render
from rest_framework.views import APIView
from .models import Game,Steam,Epicgames,Gog
from .serializers import GameSerializerList,GameSerializer,SteamSerializer,EpicGamesSerializer,GogSerializer,UserSerializer,RegisterSerializer
from rest_framework.response import Response
from rest_framework import status
from django.http import Http404
from rest_framework.permissions import AllowAny
from django.contrib.auth.models import User
from rest_framework.authentication import TokenAuthentication
from rest_framework import generics
from rest_framework.permissions import IsAuthenticated
from .permissions import IsGroupMember


class GameLetter(APIView):
    permission_classes = [IsAuthenticated]
    def get(self,request,letter,format=None):
        game = Game.objects.filter(gamename__istartswith=letter)
        serializer = GameSerializer(game, many=True)
        return Response(serializer.data)

class GameList(APIView):
    permission_classes = [IsAuthenticated]
    def get(self,request,format=None):
        game = Game.objects.all()
        serializer = GameSerializerList(game,many=True)
        return Response(serializer.data)
    
class GameAdd(APIView):
    permission_classes = [IsAuthenticated,IsGroupMember]
    def post(self,request,format=None):
        serializer = GameSerializer(data=request.data)
        if serializer.is_valid():
            serializer.save()
            return Response(serializer.data, status=status.HTTP_201_CREATED)
        return Response(serializer.errors, status=status.HTTP_400_BAD_REQUEST)
    
class GameUpdate(APIView):
     permission_classes = [IsAuthenticated,IsGroupMember]
     def get_object(self,pk):
        try:
            return Game.objects.get(pk=pk)
        except Game.DoesNotExist:
            raise Http404

     def get(self, request, pk, format=None):
        game = self.get_object(pk)
        serializer = GameSerializer(game)
        return Response(serializer.data)
     
     def put(self, request, pk, format=None):
        game = self.get_object(pk)
        serializer = GameSerializer(game, data=request.data)
        if serializer.is_valid():
            serializer.save()
            return Response(serializer.data)
        return Response(serializer.errors, status=status.HTTP_400_BAD_REQUEST)

class GameDelete(APIView):
    permission_classes = [IsAuthenticated,IsGroupMember]
    def get_object(self,pk):
        try:
            return Game.objects.get(pk=pk)
        except Game.DoesNotExist:
            raise Http404

    def get(self, request, pk, format=None):
        game = self.get_object(pk)
        serializer = GameSerializer(game)
        return Response(serializer.data)
    
    def delete(self, request, pk, format=None):
        game = self.get_object(pk)
        game.delete()
        return Response(status=status.HTTP_204_NO_CONTENT)
    

class GameGenreList(APIView):
    permission_classes = [IsAuthenticated]
    def get_object(self,pk):
        try:
            return Game.objects.get(gamegenre=pk)
        except Game.DoesNotExist:
            raise Http404


    def get(self,request,pk,format=None):
        game = Game.objects.filter(gamegenre=pk)
        serializer = GameSerializerList(game,many=True)
        return Response(serializer.data)
    
class GamePlatforms(APIView):
    permission_classes = [IsAuthenticated]
    def get_object(self,pk):
        try:
            return Game.objects.get(launchertype=pk)
        except Game.DoesNotExist:
            raise Http404
        
    def get(self,request,pk,format=None):
        game = Game.objects.filter(launchertype=pk)
        serializer = GameSerializer(game,many=True)
        return Response(serializer.data)
    
class SteamList(APIView):
    permission_classes = [IsAuthenticated]
    def get(self,request,format=None):
        steam = Steam.objects.all()
        serializer = SteamSerializer(steam,many=True)
        return Response(serializer.data)
    
class EpicGamesList(APIView):
    permission_classes = [IsAuthenticated]
    def get(self,request,format=None):
        epic = Epicgames.objects.all()
        serializer = EpicGamesSerializer(epic,many=True)
        return Response(serializer.data)
    
    
class GogList(APIView):
    def get(self,request,format=None):
        gog = Gog.objects.all()
        serializer = GogSerializer(gog,many=True)
        return Response(serializer.data)

class UserDetailAPI(APIView):
  authentication_classes = (TokenAuthentication,)
  permission_classes = (AllowAny,)
  def get(self,request,*args,**kwargs):
    user = User.objects.get(id=request.user.id)
    serializer = UserSerializer(user)
    return Response(serializer.data)
  
class RegisterUserAPIView(generics.CreateAPIView):
  permission_classes = (AllowAny,)
  serializer_class = RegisterSerializer

class UserList(generics.ListAPIView):
    permission_classes = [IsAuthenticated,IsGroupMember]
    queryset = User.objects.filter(is_staff=False)
    serializer_class = UserSerializer


class UserDetailDelete(generics.RetrieveAPIView,generics.DestroyAPIView):
    permission_classes = [IsAuthenticated,IsGroupMember]
    queryset = User.objects.filter(is_staff=False) 
    serializer_class = UserSerializer
    
    def delete(self, request, *args, **kwargs):
        return self.destroy(request, *args, **kwargs)