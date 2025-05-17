"use client"

import type React from "react"

import { useState } from "react"
import { Link, useLocation } from "react-router-dom"
import { cn } from "../lib/utils"
import { Button } from "./ui/button"
import { ScrollArea } from "./ui/scroll-area"
import { LayoutDashboard, ShoppingCart, Users, Package, BarChart, Settings, Menu, X } from "lucide-react"
import { useMobile } from "../hooks/use-mobile"

interface SidebarProps extends React.HTMLAttributes<HTMLDivElement> {}

const Sidebar: React.FC<SidebarProps> = ({ className }) => {
  const location = useLocation()
  const isMobile = useMobile()
  const [isOpen, setIsOpen] = useState(false)

  const routes = [
    {
      label: "Dashboard",
      icon: LayoutDashboard,
      href: "/dashboard",
      active: location.pathname === "/dashboard",
    },
    {
      label: "Orders",
      icon: ShoppingCart,
      href: "/orders",
      active: location.pathname.startsWith("/orders"),
    },
    {
      label: "Customers",
      icon: Users,
      href: "/customers",
      active: location.pathname.startsWith("/customers"),
    },
    {
      label: "Products",
      icon: Package,
      href: "/products",
      active: location.pathname.startsWith("/products"),
    },
    {
      label: "Analytics",
      icon: BarChart,
      href: "/analytics",
      active: location.pathname.startsWith("/analytics"),
    },
    {
      label: "Settings",
      icon: Settings,
      href: "/settings",
      active: location.pathname.startsWith("/settings"),
    },
  ]

  const toggleSidebar = () => {
    setIsOpen(!isOpen)
  }

  if (isMobile) {
    return (
      <>
        <Button variant="outline" size="icon" className="fixed left-4 top-4 z-50" onClick={toggleSidebar}>
          <Menu className="h-4 w-4" />
        </Button>

        {isOpen && <div className="fixed inset-0 z-40 bg-black/50" onClick={toggleSidebar} />}

        <aside
          className={cn(
            "fixed inset-y-0 left-0 z-50 w-64 bg-white shadow-lg transition-transform duration-300 ease-in-out",
            isOpen ? "translate-x-0" : "-translate-x-full",
            className,
          )}
        >
          <div className="flex h-16 items-center justify-between border-b px-4">
            <Link to="/dashboard" className="flex items-center gap-2 font-semibold">
              <ShoppingCart className="h-5 w-5" />
              <span>Sales Order System</span>
            </Link>
            <Button variant="ghost" size="icon" onClick={toggleSidebar}>
              <X className="h-4 w-4" />
            </Button>
          </div>
          <ScrollArea className="h-[calc(100vh-4rem)]">
            <div className="px-3 py-2">
              <nav className="flex flex-col gap-1">
                {routes.map((route) => (
                  <Link
                    key={route.href}
                    to={route.href}
                    onClick={toggleSidebar}
                    className={cn(
                      "flex items-center gap-3 rounded-lg px-3 py-2 text-sm font-medium",
                      route.active ? "bg-primary text-primary-foreground" : "hover:bg-muted",
                    )}
                  >
                    <route.icon className="h-5 w-5" />
                    {route.label}
                  </Link>
                ))}
              </nav>
            </div>
          </ScrollArea>
        </aside>
      </>
    )
  }

  return (
    <aside className={cn("w-64 border-r bg-white", className)}>
      <div className="flex h-16 items-center border-b px-4">
        <Link to="/dashboard" className="flex items-center gap-2 font-semibold">
          <ShoppingCart className="h-5 w-5" />
          <span>Sales Order System</span>
        </Link>
      </div>
      <ScrollArea className="h-[calc(100vh-4rem)]">
        <div className="px-3 py-2">
          <nav className="flex flex-col gap-1">
            {routes.map((route) => (
              <Link
                key={route.href}
                to={route.href}
                className={cn(
                  "flex items-center gap-3 rounded-lg px-3 py-2 text-sm font-medium",
                  route.active ? "bg-primary text-primary-foreground" : "hover:bg-muted",
                )}
              >
                <route.icon className="h-5 w-5" />
                {route.label}
              </Link>
            ))}
          </nav>
        </div>
      </ScrollArea>
    </aside>
  )
}

export default Sidebar
