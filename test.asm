.DEVICE ATmega64

.def reg = R16
.def reg2 = R31

; Init Stackpointer
ldi reg, LOW(RAMEND)
out SPL, reg
ldi reg, HIGH(RAMEND)
out SPH, reg

ldi reg,0xFF

out DDRC,reg
ldi reg2,0xFA
out PORTC,reg

and reg,reg2