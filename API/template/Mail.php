<?php

namespace template;

class Mail{
    private string $mailUUID;
    private string $senderUUID;
    private array $receiversUUIDs;
    private string $object;
    private string $content;
    private string $dateTimestamp;
    private array $attachment;

    public function __construct(string $mailUUID, string $senderUUID, array $receiversUUIDs, string $object, string $content, string $dateTimestamp, array $attachment){
        $this->mailUUID = $mailUUID;
        $this->senderUUID = $senderUUID;
        $this->receiversUUIDs = $receiversUUIDs;
        $this->object = $object;
        $this->content = $content;
        $this->dateTimestamp = $dateTimestamp;
        $this->attachment = $attachment;
    }

    public function getMailUUID(): string{return $this->mailUUID;}
    public function getSenderUUID(): string{return $this->senderUUID;}
    public function getReceiversUUIDs(): array{return $this->receiversUUIDs;}

    public function getObject(): string{return $this->object;}
    public function getContent(): string{return $this->content;}
    public function getDateTimestamp(): string{return $this->getDateTimestamp();}
    public function getAttachment(): array{return $this->attachment;}
}