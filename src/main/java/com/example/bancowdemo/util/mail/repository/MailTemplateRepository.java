package com.example.bancowdemo.util.mail.repository;

import com.example.bancowdemo.util.mail.entity.MailTemplate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MailTemplateRepository extends JpaRepository<MailTemplate, Long> {
    Optional<MailTemplate> findByTemplateId(String templateId);
}
