import { NativeModules } from "react-native";

const { PagSeguroBridge: PagSeguroBridgeNativeModules } = NativeModules;

const PagSeguroBridge = {
  printFile: async (path, printerQuality = 1, marginBottom = 100) => {
    console.log({
      path,
      printerQuality,
      marginBottom,
    });
    await PagSeguroBridgeNativeModules.printFile({
      path,
      printerQuality,
      marginBottom,
    });
  },
};

export default PagSeguroBridge;
