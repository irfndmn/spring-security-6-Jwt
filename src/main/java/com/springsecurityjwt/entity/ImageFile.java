package com.springsecurityjwt.entity;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "t_imageFiles")
@Builder(toBuilder = true)
public class ImageFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Lob
    private byte[] data;

    private String contentType;

    private boolean isDefault;

}