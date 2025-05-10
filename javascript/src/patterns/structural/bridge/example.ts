interface Device {
    isEnabled(): boolean;
    enable(): void;
    disable(): void;
    getVolume(): number;
    setVolume(percent: number): void;
    getChannel(): number;
    setChannel(channel: number): void;
}

export class TV implements Device {
    private enabled = false;
    private volume = 50;
    private channel = 1;

    isEnabled(): boolean { return this.enabled; }
    enable(): void { this.enabled = true; }
    disable(): void { this.enabled = false; }
    
    getVolume(): number { return this.volume; }
    setVolume(percent: number): void { 
        this.volume = Math.max(0, Math.min(100, percent)); 
    }
    
    getChannel(): number { return this.channel; }
    setChannel(channel: number): void { this.channel = channel; }
}

export class Radio implements Device {
    private on = false;
    private soundLevel = 30;
    private currentStation = 88.5;

    isEnabled(): boolean { return this.on; }
    enable(): void { this.on = true; }
    disable(): void { this.on = false; }
    
    getVolume(): number { return this.soundLevel; }
    setVolume(percent: number): void { 
        this.soundLevel = Math.max(0, Math.min(100, percent)); 
    }
    
    getChannel(): number { return this.currentStation; }
    setChannel(channel: number): void { 
        this.currentStation = channel; 
    }
}

abstract class RemoteControl {
    protected device: Device;

    constructor(device: Device) {
        this.device = device;
    }

    togglePower(): void {
        if (this.device.isEnabled()) {
            this.device.disable();
        } else {
            this.device.enable();
        }
    }

    volumeDown(): void {
        this.device.setVolume(this.device.getVolume() - 10);
    }

    volumeUp(): void {
        this.device.setVolume(this.device.getVolume() + 10);
    }

    channelDown(): void {
        this.device.setChannel(this.device.getChannel() - 1);
    }

    channelUp(): void {
        this.device.setChannel(this.device.getChannel() + 1);
    }
}

export class BasicRemote extends RemoteControl {
    constructor(device: Device) {
        super(device);
    }
}

export class AdvancedRemote extends RemoteControl {
    constructor(device: Device) {
        super(device);
    }

    mute(): void {
        this.device.setVolume(0);
    }

    presetChannel(channel: number): void {
        this.device.setChannel(channel);
    }
}