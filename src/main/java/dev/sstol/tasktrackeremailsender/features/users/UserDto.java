package dev.sstol.tasktrackeremailsender.features.users;

import java.io.Serializable;

public record UserDto(Long id, String email) implements Serializable {
}