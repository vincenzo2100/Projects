from django.urls import path, include
from . import views
from .views import UserDetailAPI,RegisterUserAPIView

urlpatterns = [
    path('game/read/', views.GameList.as_view()),
    path('game/read/<str:letter>/', views.GameLetter.as_view()),
    path('game/add/', views.GameAdd.as_view()),
    path('game/update/<int:pk>/', views.GameUpdate.as_view()),
    path('game/delete/<int:pk>/', views.GameDelete.as_view()),
    path('game/genre/<int:pk>/', views.GameGenreList.as_view()),
    path('game/platform/steam', views.SteamList.as_view()),
    path('game/platform/epicgames', views.EpicGamesList.as_view()),
    path('game/platform/gog', views.GogList.as_view()),
    path('game/register',RegisterUserAPIView.as_view()),
    path('users/', views.UserList.as_view()),
    path('users/delete/<int:pk>/', views.UserDetailDelete.as_view()),

]