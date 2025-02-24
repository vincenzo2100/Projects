<?php

namespace App\Controller;

use Doctrine\Persistence\ManagerRegistry;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\JsonResponse;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\PasswordHasher\Hasher\UserPasswordHasherInterface;
use Symfony\Component\Routing\Attribute\Route;
use Symfony\Component\Security\Http\Attribute\CurrentUser;
use Lexik\Bundle\JWTAuthenticationBundle\Services\JWTTokenManagerInterface;


use App\Entity\User;
use App\Entity\Teacher;
use App\Entity\Student;

class RegisterController extends AbstractController
{


    #[Route('api/register/student', name: 'register_student', methods: 'post')]
    public function registerStudent(ManagerRegistry $doctrine, Request $request, UserPasswordHasherInterface $passwordHasher): JsonResponse
    {
        
        $em = $doctrine->getManager();
        $decoded = json_decode($request->getContent());
        $requiredFields = ['email', 'password', 'firstName', 'lastName'];
        foreach ($requiredFields as $field) {
            if (!property_exists($decoded, $field)) {
                return $this->json(['error' => "Missing required field: $field"], Response::HTTP_BAD_REQUEST);
            }
        }

       
        $email = $decoded->email;
        $plaintextPassword = $decoded->password;
        $firstName = $decoded->firstName;
        $lastName = $decoded->lastName;
        $user = new Student();
        $hashedPassword = $passwordHasher->hashPassword(
            $user,
            $plaintextPassword
        );
        if (!filter_var($email, FILTER_VALIDATE_EMAIL)) {
            return $this->json(['error' => 'Invalid email format'], Response::HTTP_BAD_REQUEST);
        }

        $existingUser = $em->getRepository(User::class)->findOneBy(['email' => $email]);
        if ($existingUser) {
            return $this->json(['error' => 'Email already exists'], Response::HTTP_BAD_REQUEST);
        }

        $user->setPassword($hashedPassword);
        $user->setEmail($email);
        $user->setUsername($email);
        $user->setFirstName($firstName);
        $user->setLastName($lastName);
        $user->setRoles(['ROLE_STUDENT']);
        $user->setPassword($hashedPassword);
        $em->persist($user);
        $em->flush();
   
        return $this->json(['message' => 'Registered Successfully']);
    }

    #[Route('api/register/teacher', name: 'register_teacher', methods: 'post')]
    public function registerTeacher(ManagerRegistry $doctrine, Request $request, UserPasswordHasherInterface $passwordHasher): JsonResponse
    {
        
        $em = $doctrine->getManager();
        $decoded = json_decode($request->getContent());
        $requiredFields = ['email', 'password', 'firstName', 'lastName'];
        foreach ($requiredFields as $field) {
            if (!property_exists($decoded, $field)) {
                return $this->json(['error' => "Missing required field: $field"], Response::HTTP_BAD_REQUEST);
            }
        }

       
        $email = $decoded->email;
        $plaintextPassword = $decoded->password;
        $firstName = $decoded->firstName;
        $lastName = $decoded->lastName;
        $user = new Teacher();
        $hashedPassword = $passwordHasher->hashPassword(
            $user,
            $plaintextPassword
        );
        if (!filter_var($email, FILTER_VALIDATE_EMAIL)) {
            return $this->json(['error' => 'Invalid email format'], Response::HTTP_BAD_REQUEST);
        }

        $existingUser = $em->getRepository(User::class)->findOneBy(['email' => $email]);
        if ($existingUser) {
            return $this->json(['error' => 'Email already exists'], Response::HTTP_BAD_REQUEST);
        }

        $user->setPassword($hashedPassword);
        $user->setEmail($email);
        $user->setUsername($email);
        $user->setFirstName($firstName);
        $user->setLastName($lastName);
        $user->setRoles(['ROLE_TEACHER']);
        $user->setPassword($hashedPassword);
        $em->persist($user);
        $em->flush();
   
        return $this->json(['message' => 'Registered Successfully']);
    }


    #[Route('api/login', name: 'app_login', methods: ['POST'])]
    public function login(#[CurrentUser] $user = null,ManagerRegistry $doctrine, Request $request,UserPasswordHasherInterface $passwordHasher,JWTTokenManagerInterface $jwtManager): Response
    {
        $em = $doctrine->getManager();
        $decoded = json_decode($request->getContent());
        $email = $decoded->email;
        $plaintextPassword = $decoded->password;
        
        // Find user by email
        $userRepository = $em->getRepository(User::class);
        $user = $userRepository->findOneBy(['email' => $email]);

        if (!$user || !$passwordHasher->isPasswordValid($user, $plaintextPassword )) {
            return $this->json(['error' => 'Invalid email or password.'], Response::HTTP_UNAUTHORIZED);
        }

        
        
        // You can proceed with login here
        $token = $jwtManager->create($user);
        return $this->json([
            'message' => 'Logged in successfully',
            'user' => [
                'id' => $user->getId(),
                'email' => $user->getEmail(),
                // Add any other user data you want to return
            ],
            "token" => $token
        ]);
    }
}
