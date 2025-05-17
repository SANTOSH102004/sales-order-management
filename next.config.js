/** @type {import('next').NextConfig} */
const nextConfig = {
  reactStrictMode: true,
  swcMinify: true,
  experimental: {
    appDir: true,
  },
  // This is important to prevent Next.js from trying to use the pages directory
  // when we're using the app directory for routing
  pageExtensions: ["tsx", "ts", "jsx", "js"].filter((extension) => {
    return !extension.includes("page")
  }),
  eslint: {
    ignoreDuringBuilds: true,
  },
  typescript: {
    ignoreBuildErrors: true,
  },
  images: {
    unoptimized: true,
  },
}

module.exports = nextConfig
