package com.orizon.webdriver.domain.valueobjects;

import com.orizon.webdriver.domain.model.file.AbstractFile;
import com.orizon.webdriver.domain.model.user.AbstractUser;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.Instant;


@Entity
@Table(name = "shared_files")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public final class ShareFile {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    private AbstractUser owner;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receiver_id")
    private AbstractUser receiver;


    @Column(name = "send_date")
    private Instant date;

    public ShareFile(AbstractUser owner, AbstractUser receiver, AbstractFile file){


    }

}
