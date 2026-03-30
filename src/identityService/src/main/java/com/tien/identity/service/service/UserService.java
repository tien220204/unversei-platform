package com.tien.identity.service.service;

import java.util.HashSet;
import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.tien.identity.service.dto.request.UserCreationRequest;
import com.tien.identity.service.dto.request.UserUpdateRequest;
import com.tien.identity.service.dto.response.UserResponse;
import com.tien.identity.service.entity.User;
import com.tien.identity.service.exception.AppException;
import com.tien.identity.service.exception.ErrorCode;
import com.tien.identity.service.mapper.UserMapper;
import com.tien.identity.service.repository.RoleRepository;
import com.tien.identity.service.repository.UserRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserService {
    UserRepository userRepository;
    UserMapper userMapper;
    RoleRepository roleRepository;
    PasswordEncoder passwordEncoder;

    public UserResponse createRequest(UserCreationRequest request) {
        var user = userMapper.toUser(request);

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        try{
            user = userRepository.save(user);
        }catch (DataIntegrityViolationException e){
            throw new AppException(ErrorCode.USER_EXISTED);
        }

        return userMapper.toUserReponse(user);
    }

    // lay info cua tai khoan dang dang nhap trong securityContextHolder
    public UserResponse getMyInfo() {
        var context = SecurityContextHolder.getContext();
        var role = context.getAuthentication();
        System.out.println(role);
        var name = context.getAuthentication().getName();
        User user = userRepository.findByUsername(name).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        return userMapper.toUserReponse(user);
    }

    @PostAuthorize("returnObject.username == authentication.name")
    public UserResponse updateRequest(String id, UserUpdateRequest request) {
        var user = userRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        request.setPassword(passwordEncoder.encode(request.getPassword()));

        var roles = roleRepository.findAllById(request.getRoles());
        user.setRoles(new HashSet<>(roles));

        userMapper.updateUser(user, request);

        return userMapper.toUserReponse(userRepository.save(user));
    }
    // xac thuc truoc khi thuc hien
    @PreAuthorize("hasRole('ADMIN')")
    public List<UserResponse> getUsers() {
        return userRepository.findAll().stream().map(userMapper::toUserReponse).toList();
    }
    // thuc hien -> xac thuc va dua ra du lieu neu xac thuc dung
    // tra ve du lieu neu username nguoi truy xuat(token) = username dang duoc tim kiem
    // return object o day la User va authentication.name là subject trong payload
    @PostAuthorize("returnObject.username  == authentication.name")
    public UserResponse getUser(String id) {
        return userMapper.toUserReponse(
                userRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED)));
    }

    public void deleteUser(String id) {
        userRepository.deleteById(id);
    }
}
