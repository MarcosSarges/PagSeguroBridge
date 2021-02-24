# react-native-pagseguro-bridge

_Suporte somente ao Android_

> A Lib serve como bridge do método **printFromFile** da maquina SmartPOS

## Instalação

`$ yarn react-native-pagseguro-bridge`

**Adicione no AndroidManifest**

```xml
<uses-permission android:name="br.com.uol.pagseguro.permission.MANAGE_PAYMENTS"/>
```

**Adicione na Activity principal**

```xml
<intent-filter>
    <action android:name="br.com.uol.pagseguro.PAYMENT"/>
    <category android:name="android.intent.category.DEFAULT"/>
</intent-filter>
```

## Uso

```javascript
import PagSeguroBridge from "react-native-pagseguro-bridge";
```

### Doc de referencia

[SmartPOS](https://dev.pagseguro.uol.com.br/reference/smart-pos#smart-pos-introducao)
