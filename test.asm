.DEVICE ATmega64

.def reg = R16

; Init Stackpointer
ldi reg, LOW(RAMEND)
out SPL, reg
ldi reg, HIGH(RAMEND)
out SPH, reg

ldi reg,0xFF

out DDRC,reg
ldi reg,0x00
out PORTC,reg